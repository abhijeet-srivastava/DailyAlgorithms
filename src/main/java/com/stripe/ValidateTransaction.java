package com.stripe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ValidateTransaction {

    String charge = "[\"CHARGE:card_country=US&currency=USD&amount=2500&ip_country=CA\",\"ALLOW:amount>500ANDip_country==CA\",\"BLOCK:card_country==CAORcard_country==MA\",  ]\n";

    String charge1 = "[\"CHARGE:card_country=US&currency=USD&amount=2500&ip_country=CA\",\"ALLOW:amount>500ANDip_country==CA\",\"BLOCK:card_country==USANDamount<200\",  ]\n";
    String charge2 = "[\"CHARGE:card_country=US&currency=USD&amount=2500&ip_country=CA\",\"ALLOW:currency==EUR\",  ]\n";
    String charge3 = "[\"CHARGE:card_country=US&currency=USD&amount=2500&ip_country=CA\",\"BLOCK:amount>500\",  ]\n";
    String charge4 = "[\"CHARGE:card_country=US&currency=USD&amount=2500&ip_country=CA\",\"ALLOW:amount>500ANDamount<2501\",  ]\n";
    String charge5 = "[\"CHARGE:card_country=US&currency=USD&amount=505&ip_country=CA\",\"ALLOW:amount>500ANDip_country==CA\",\"BLOCK:card_country==CAANDamount<200\",  ]\n";
    String charge6 = "[\"CHARGE:card_country=US&currency=USD&amount=505&ip_country=CA\",\"ALLOW:amount>500ANDamount==999ANDip_country==CA\",\"BLOCK:card_country==MAANDamount<200\",  ]\n";
    String charge7 = "[\"CHARGE:card_country=US&currency=USD&amount=999&ip_country=CA\",\"ALLOW:amount>500ANDamount==999ANDip_country==CA\",\"BLOCK:card_country==MAANDamount<200\",  ]\n";

    public static void main(String[] args) {
        ValidateTransaction vt = new ValidateTransaction();
        vt.parse();
        //vt.testParseToken();
    }

    private void testParseToken() {
        String tokenStr = "amount>500ANDip_country==CAORcurrency==EUR";
        String[] parsed = tokenStr.split("AND|OR");
        Predicate<Transaction> predicate;
        List<String[]> list = new ArrayList<>();
        for(int i = 0; i < parsed.length; i++) {
            String[] arr = new String[2];
            arr[0] = parsed[i];
            tokenStr = tokenStr.substring(parsed[i].length());
            if(tokenStr.startsWith("AND")) {
                arr[1] = "AND";
            } else {
                arr[1] = "OR";
            }
            list.add(arr);
        }
        System.out.printf("%d\n", list.size());
    }

    private void parse( ) {
        Map<String, String> transaction;
        String[] first = charge2.split("\"");
        Predicate<Transaction> allowed;
        Predicate<String> blocked;
        for(int i = 0; i < first.length; i++) {
            if(i%2 == 0) {
                continue;
            }
            System.out.printf("[%s]\n", first[i]);
            if(first[i].startsWith("CHARGE:")) {
                transaction = parseTransaction(first[i].trim().substring("CHARGE:".length()));
                System.out.printf("Transactions: %d\n", transaction.size());
            } else if(first[i].trim().startsWith("ALLOW:")) {
                String allowedStr = first[i].trim().substring("ALLOW:".length());
                String[] tokens = allowedStr.split("AND|OR");


            } else if(first[i].startsWith("BLOCK:")) {

            }
        }

    }



    private Map<String, String> parseTransaction(String txnStr) {
        String[] txns = txnStr.split("&");
        Map<String, String> map = new HashMap<>();
        for(String tx : txns) {
            String[] kv = tx.split("=");
            map.put(kv[0].trim(), kv[1].trim());
        }
        return map;
    }
    private class Transaction {
        String cardCountry;
        String currency;
        Integer amount;
        String ipCountry;
        public Transaction(Map<String, String > map) {
            this.cardCountry = map.get("card_country");
            this.currency = map.get("currency");
            this.amount = Integer.parseInt(map.get("amount"));
            this.ipCountry = map.get("ip_country");
        }
        public boolean evaluate(String str) {
            if(str.startsWith("amount")) {
                return evaluateAmount(str.substring("amount".length()));
            } else if(str.startsWith("card_country")) {
                return evaluateCardCountry(str.substring("card_country".length()+2));
            } else if(str.startsWith("currency")) {
                return evaluateCurrency(str.substring("currency".length()+2));
            } else if(str.startsWith("ip_country")) {
                return evaluateIpCountry(str.substring("ip_country".length()+2));
            }
            return false;
        }

        private boolean evaluateIpCountry(String ipCountry) {
            return this.ipCountry.equals(ipCountry);
        }

        private boolean evaluateCurrency(String currency) {
            return this.currency.equals(currency);
        }

        private boolean evaluateCardCountry(String cardCountry) {
            return this.cardCountry.equals(cardCountry);
        }

        private boolean evaluateAmount(String amountCond) {
            if(amountCond.charAt(0) == '=') {
                return this.amount == Integer.parseInt(amountCond.substring(1));
            } else if(amountCond.charAt(0) == '>' && amountCond.charAt(1) == '=') {
                return this.amount >= Integer.parseInt(amountCond.substring(2));
            } else if(amountCond.charAt(0) == '>') {
                return this.amount > Integer.parseInt(amountCond.substring(1));
            }else  if(amountCond.charAt(0) == '<' && amountCond.charAt(1) == '=') {
                return this.amount <= Integer.parseInt(amountCond.substring(2));
            } else if(amountCond.charAt(0) == '<') {
                return this.amount < Integer.parseInt(amountCond.substring(1));
            }
            return true;
        }
    }
}

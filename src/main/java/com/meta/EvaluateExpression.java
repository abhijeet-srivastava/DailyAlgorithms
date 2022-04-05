package com.meta;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EvaluateExpression {

    public static void main(String[] args) {
        EvaluateExpression exp = new EvaluateExpression();
        String s = "cbadbc" ;
        Map<Character, Integer> lastIndex = new HashMap<>();
        for(int i = s.length()-1; i >= 0; i--) {
            int finalI = i;
            lastIndex.computeIfAbsent(s.charAt(i), (e) -> finalI);
        }
        System.out.printf("%s\n", lastIndex.entrySet().stream().map(e -> String.format("%c - %d", e.getKey(), e.getValue())).collect(Collectors.joining(", ")));
        exp.testExpression();
    }

    private void testExpression() {
        int res = evaluateExpr("1*2+3*5+3*2");
        System.out.printf("Res: %d\n", res);
    }

    private int evaluateExpr(String expr) {
        int result = 0;
        int currNum = 0;
        char prevOperator = '+';
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if(Character.isDigit(ch)) {
                currNum = currNum * 10 + Character.getNumericValue(ch);
            }else if(prevOperator == '+') {
                stack.push(currNum);
                prevOperator  = ch;
                currNum = 0;
            } else {
                stack.push(stack.pop()*currNum);
                prevOperator = ch;
                currNum = 0;
            }
        }
        if(prevOperator == '*') {
            stack.push(stack.pop()*currNum);
        } else {
            stack.push(currNum);
        }
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private Integer evaluate(int operand1, int operand2, char operator) {
        if(operator == '+') {
            return operand1 + operand2;
        }
        return operand1*operand2;
    }
}

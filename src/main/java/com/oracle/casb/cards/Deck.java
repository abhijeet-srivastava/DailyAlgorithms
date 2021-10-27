package com.oracle.casb.cards;

import java.util.Collections;
import java.util.List;

/**
 * Created By : abhijsri
 * Date  : 09/12/18
 **/
public class Deck<T extends Card> {

    private List<T> cards; // all cards, dealt or not
    private int dealtlndex = 0; // marks first undealt card

    public void setDeckOfCards(List<T> deckOfCards) {
        this.cards = deckOfCards;
    }

    public void shuffle() {
        //cards = CardShuffles.riffleShuffle(cards, 20);
        Collections.shuffle(cards);
    }

    public int remainingCards() {
        return cards.size() - dealtlndex;
    }

    //public T[] dealHand(int number) { ...}

    //public T dealCard() { ...}

}

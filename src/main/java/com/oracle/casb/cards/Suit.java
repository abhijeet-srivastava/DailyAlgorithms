package com.oracle.casb.cards;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum Suit {

    Club(0),
    Diamond(1),
    Heart(2),
    Spade(3);

    private static Map<Integer, Suit> SUIT_MAPPER
            = ImmutableMap.of(0, Club, 1, Diamond, 2, Heart, 3, Spade);
    private int value;

    private Suit(int v) {
        value = v;
    }

    public int getValue() {
        return value;
    }

    public static Suit getSuitFromValue(int value) {
      return SUIT_MAPPER.get(Integer.valueOf(value));
    }
}

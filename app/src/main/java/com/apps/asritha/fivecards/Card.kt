package com.apps.asritha.fivecards;

import java.util.LinkedList;

/**
 * Created by Asritha on 05-05-2016.
 */
public class Card {

    public enum Rank {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}

    public static LinkedList<Card> deck = new LinkedList<Card>();

    public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}

    static Rank[] vals = Rank.values();

    static {
        int i = 0, x;
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values()) {
                x = rank.ordinal();
                if (x == 10 || x == 11 || x == 12)
                    x = 9;
                deck.add(new Card(rank, suit, i, x + 1));
                i++;
            }
        deck.add(new Card(null, null, 52, 0));
        deck.add(new Card(null, null, 53, 0));
    }

    public Card(Rank rank, Suit suit, int x, int n) {
        this.rank = rank;
        this.suit = suit;
        this.drawableId = x;
        this.value = n;
    }

    public static Rank next(Rank r) {
        return vals[(r.ordinal() + 1) % vals.length];
    }

    private Rank rank;
    private Suit suit;
    private int drawableId;
    private int value;

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    public int id() {
        return drawableId;
    }

    public int value() {
        return value;
    }

    public static LinkedList<Card> newDeck() {
        return new LinkedList<Card>(deck);// Return copy of prototype deck
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author mykee
 */
public class Deck {

    private final List<Card> cards;
    private int top_index;

    public Deck() {
        cards = new ArrayList<>();
        top_index = 0;

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                cards.add(new Card(s, r));
            }
        }
    }

    /**
     *
     * Shuffles the deck, so the cards in it will be in random order.
     * Also sets the counter for drawn cards to 0.
     */
    public void shuffle() {
        Collections.shuffle(cards);
        top_index = 0;
    }

    /**
     * 
     * @return true, if the Deck is considered empty (all Cards have been drawn from it)
     */
    public boolean isEmpty() {
        return top_index >= 52;
    }

    /**
     *
     * @return The Card found at the top of the Deck.
     * @throws IndexOutOfBoundsException if the deck is empty.
     */
    public Card getTopCard() {
        Card c = cards.get(top_index);
        ++top_index;
        return c;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        cards.forEach(sb::append);
        return sb.toString();
    }
}

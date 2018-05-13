/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author mykee
 */
public class DeckTests {

    Deck d;

    @Before
    public void setUpDeck() {
        d = new Deck();
    }

    @Test
    public void testNewDeck() {
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                assertEquals(d.getTopCard(), new Card(s, r));
            }
        }
    }

    @Test
    public void testTopCard() {
        assertEquals(d.getTopCard(), new Card(Suit.CLUBS, Rank.TWO));
    }

    @Test
    public void testDeckEmpty() {
        for (int i = 0; i < 52; ++i) {
            d.getTopCard();
        }

        assertTrue(d.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeckException() {
        for (int i = 0; i < 52; ++i) {
            d.getTopCard();
        }
        d.getTopCard();
        fail("should never happen");
    }

    @Test
    public void testShuffle() {
        for (int i = 0; i < 52; ++i) {
            d.getTopCard();
        }

        assertTrue(d.isEmpty());
        d.shuffle();
        assertFalse(d.isEmpty());

    }
}

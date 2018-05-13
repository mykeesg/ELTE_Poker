/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mykee
 */
public class CardTests {

    @Test
    public void testCardRank() {
        for (Rank r : Rank.values()) {
            Card c = new Card(Suit.CLUBS, r);
            assertEquals(c.getRank(), r);
        }
    }
    
    @Test
    public void testCardSuit(){
        for (Suit s: Suit.values()) {
            Card c = new Card(s, Rank.ACE);
            assertEquals(c.getSuit(), s);
        }
    }
}

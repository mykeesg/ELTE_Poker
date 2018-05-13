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
public class EvaluatorTests {

    @Test
    public void testHighCard() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.JACK),
            new Card(Suit.CLUBS, Rank.ACE),
            new Card(Suit.SPADES, Rank.THREE),
            new Card(Suit.CLUBS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.SIX)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.HIGH_CARD);
    }

    @Test
    public void testOnePair() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.EIGHT),
            new Card(Suit.CLUBS, Rank.QUEEN),
            new Card(Suit.SPADES, Rank.THREE),
            new Card(Suit.CLUBS, Rank.SIX),
            new Card(Suit.CLUBS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.TEN)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.ONE_PAIR);
    }

    @Test
    public void testTwoPairs() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.TEN),
            new Card(Suit.CLUBS, Rank.ACE),
            new Card(Suit.SPADES, Rank.ACE),
            new Card(Suit.CLUBS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.THREE)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.TWO_PAIRS);
    }

    @Test
    public void testThreeOfAKind() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.TEN),
            new Card(Suit.CLUBS, Rank.ACE),
            new Card(Suit.SPADES, Rank.SIX),
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.THREE)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.THREE_OF_A_KIND);
    }

    @Test
    public void testStraight() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.SIX),
            new Card(Suit.CLUBS, Rank.FIVE),
            new Card(Suit.SPADES, Rank.ACE),
            new Card(Suit.CLUBS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.THREE)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.STRAIGHT);
    }

    @Test
    public void testFlush() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.QUEEN),
            new Card(Suit.CLUBS, Rank.ACE),
            new Card(Suit.HEARTS, Rank.SIX),
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.HEARTS, Rank.THREE)
        };

        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.FLUSH);
    }

    @Test
    public void testFullHouse() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.TEN),
            new Card(Suit.CLUBS, Rank.ACE),
            new Card(Suit.SPADES, Rank.SIX),
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.ACE)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.FULL_HOUSE);
    }

    @Test
    public void testPoker() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.ACE),
            new Card(Suit.SPADES, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.SEVEN),
            new Card(Suit.DIAMONDS, Rank.FOUR),
            new Card(Suit.CLUBS, Rank.ACE)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.POKER);
    }

    @Test
    public void testStraightFlush() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.SIX),
            new Card(Suit.DIAMONDS, Rank.FIVE),
            new Card(Suit.SPADES, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.SEVEN),
            new Card(Suit.DIAMONDS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.THREE)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.STRAIGHT_FLUSH);
    }

    @Test
    public void testRoyalFlush() {
        final Card[] hand = {
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.SIX),
            new Card(Suit.HEARTS, Rank.JACK),
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.QUEEN),
            new Card(Suit.HEARTS, Rank.KING)
        };
        Result r = Evaluator.getResult(hand);
        assertEquals(r.getRank(), HandRank.ROYAL_FLUSH);
    }

}

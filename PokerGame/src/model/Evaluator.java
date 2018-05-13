/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mykee
 */
public class Evaluator {

    /**
     *
     * @param hand
     * @return the Sum of the cards' ranks in the given hand (by default
     * ordering)
     */
    protected static int getSum(Card[] hand) {
        int val = 0;
        for (Card c : hand) {
            val += c.getRank().asInt();
        }
        return val;
    }

    /**
     *
     * @param given 5 Cards we would like to eval
     * @return The result of the hand. The determination can be a bit long, but
     * have to be sure.
     */
    protected static Result getRank(Card[] given) {

//we have to copy it, so the original order won't be screwed up
        Card[] hand = new Card[5];
        for (int i = 0; i < 5; ++i) {
            hand[i] = given[i];
        }

        //easier to check things if they are ordered
        Arrays.sort(hand);

        Result ret = new Result();
        ret.setCards(hand);

        Map<Rank, Integer> ranks = new HashMap<>();
        for (Rank r : Rank.values()) {
            ranks.put(r, 0);
        }
        Map<Suit, Integer> suits = new HashMap<>();
        for (Suit s : Suit.values()) {
            suits.put(s, 0);
        }

        //we calculate how many of each is present
        for (Card c : hand) {
            ranks.put(c.getRank(), ranks.get(c.getRank()) + 1);
            suits.put(c.getSuit(), suits.get(c.getSuit()) + 1);
        }

        boolean flush = false;
        boolean straight = false;
        boolean three = false;
        boolean pairs = false;

        //check for flush
        for (Suit s : Suit.values()) {
            if (suits.get(s) == 5) {
                flush = true;
            }
        }

        //straight
        //a bit ugly, but we have to check if there are 5 cards in a row from one end to another
        Rank[] all_ranks = Rank.values();
        for (int i = all_ranks.length - 1; i >= 4; --i) {
            if (ranks.get(all_ranks[i]) == 1 && ranks.get(all_ranks[i - 1]) == 1 && ranks.get(all_ranks[i - 2]) == 1 && ranks.get(all_ranks[i - 3]) == 1 && ranks.get(all_ranks[i - 4]) == 1) {
                straight = true;
            }
        }

        //straight
        //because ACE can be a start of a straight as well, it needs a special case
        if (ranks.get(Rank.ACE) == 1 && ranks.get(Rank.TWO) == 1 && ranks.get(Rank.THREE) == 1 && ranks.get(Rank.FOUR) == 1 && ranks.get(Rank.FIVE) == 1) {
            straight = true;
        }

        if (flush && straight) {
            ret.setScore(getSum(hand));
            ret.setRank(ranks.get(Rank.ACE) == 1 ? HandRank.ROYAL_FLUSH : HandRank.STRAIGHT_FLUSH);
            return ret;
        }

        if (straight) {
            ret.setScore(getSum(hand));
            ret.setRank(HandRank.STRAIGHT);
            return ret;
        }

        if (flush) {
            ret.setScore(getSum(hand));
            ret.setRank(HandRank.FLUSH);
            return ret;
        }

        //poker
        for (Rank r : Rank.values()) {
            if (ranks.get(r) == 4) {
                //magic numbers
                int score = 400 * r.asInt() + 2;
                score += hand[0].getRank() == hand[1].getRank() ? (hand[4].getRank().asInt() + 2) : (hand[0].getRank().asInt() + 2);

                ret.setScore(score);
                ret.setRank(HandRank.POKER);
                return ret;
            }
        }

        for (Rank r : Rank.values()) {
            if (ranks.get(r) == 3) {
                three = true;
            }
        }

        int pair_count = 0;
        for (Rank r : Rank.values()) {
            if (ranks.get(r) == 2) {
                pairs = true;
                ++pair_count;
            }
        }

        if (three && pairs) {
            int score = 0;
            for (Rank r : Rank.values()) {
                if (ranks.get(r) > 0) {
                    score += ranks.get(r) == 3 ? 300 * (r.asInt() + 2) : 2 * (r.asInt() + 2);
                }
            }
            ret.setScore(score);
            ret.setRank(HandRank.FULL_HOUSE);
            return ret;
        }

        if (three && !pairs) {
            int score = 0;
            for (Rank r : Rank.values()) {
                if (ranks.get(r) > 0) {
                    score += ranks.get(r) == 3 ? 300 * (r.asInt() + 2) : (r.asInt() + 2);
                }
            }
            ret.setScore(score);

            ret.setRank(HandRank.THREE_OF_A_KIND);
            return ret;
        }

        if (pairs) {
            int score = 0;
            for (Rank r : Rank.values()) {
                if (ranks.get(r) > 0) {
                    score += ranks.get(r) == 2 ? 200 * (r.asInt() + 2) : 2 * (r.asInt() + 2);
                }
            }
            ret.setScore(score);

            ret.setRank(pair_count == 2 ? HandRank.TWO_PAIRS : HandRank.ONE_PAIR);
            return ret;
        }

        ret.setRank(HandRank.HIGH_CARD);
        ret.setScore(getSum(hand));
        return ret;

    }

    public static Result getResult(Card[] hands) {
        Result ret = new Result();

        Card[] current = new Card[5];
        /**
         * We have to check the player's hand by evaluating every possible
         * 5-Card combination made from the 7 cards he plays with, and the best
         * one will be his final result.
         */
        for (int i = 0; i < 6; ++i) {
            for (int j = i + 1; j < 7; ++j) {

                int k = 0;

                //we always exclude 2 cards
                for (int m = 0; m < 7; ++m) {
                    if (m != i && m != j) {
                        current[k++] = hands[m];
                    }
                }

                //evaluate the 5 cards
                Result curr_res = getRank(current);

                //it's better than the one he had so far
                if (curr_res.compareTo(ret) > 0) {
                    ret = curr_res;
                }
            }
        }

        return ret;
    }

    protected Evaluator() {
    }
}

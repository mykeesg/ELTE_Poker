/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mykee
 */
public class Result implements Comparable<Result> {

    protected int score;

    public void setScore(int score) {
        this.score = score;
    }

    public void setRank(HandRank rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public Card[] getCards() {
        return cards;
    }

    public HandRank getRank() {
        return rank;
    }
    protected Card[] cards;
    protected HandRank rank;

    public Result() {
        rank = HandRank.NONE;
        score = 0;
    }

    public void setCards(Card[] c) {
        cards = c;
    }

    @Override
    public int compareTo(Result t) {
        if (rank.ordinal() < t.rank.ordinal()) {
            return -1;
        }
        if (rank.ordinal() == t.rank.ordinal()) {
            return score - t.score;
        }
        return 1;
    }

    public boolean contains(Card other) {
        for (Card c : cards) {
            if (c.equals(other)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rank);
        sb.append(" with {");
        for (Card c : cards) {
            sb.append(c);
        }
        sb.append("}");

        return sb.toString();
    }
}

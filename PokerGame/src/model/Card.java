/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author mykee
 */
public class Card implements Comparable<Card> {

    protected final Suit suit;
    protected final Rank rank;

    /**
     * 
     * @return The suit of the Card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * 
     * @return The Rank of the Card.
     */
    public Rank getRank() {
        return rank;
    }

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public Card(Card other){
        this.suit = other.suit;
        this.rank = other.rank;
    }

    @Override
    public String toString() {
        return "{" + rank.toString() + " of " + suit.toString() + "}";
    }

    @Override
    public int compareTo(Card t) {
        return rank.ordinal() - t.rank.ordinal();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.suit);
        hash = 53 * hash + Objects.hashCode(this.rank);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        return (this.suit==other.suit) && (this.rank==other.rank);
    }

}

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
public enum Rank {
    TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'),
    TEN('T'), JACK('J'), QUEEN('Q'), KING('K'), ACE('A');
    
    public final char c;

    private Rank(char c) {
        this.c = c;
    }

    /**
     *
     * @return the ordinal of the Rank
     */
    public int asInt() {
        return this.ordinal();
    }

    /**
     *
     * @return the Char representation of this Rank
     */
    public char asChar() {
        return c;
    }
    
    @Override
    public String toString() {
        String str = super.toString().toLowerCase();        
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
}

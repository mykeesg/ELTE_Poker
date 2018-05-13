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
public enum HandRank implements Comparable<HandRank> {
    NONE("None"), HIGH_CARD("High Card"), ONE_PAIR("One pair"), TWO_PAIRS("Two pairs"), THREE_OF_A_KIND("Three of a Kind"),
    STRAIGHT("Straight"), FLUSH("Flush"), FULL_HOUSE("Full House"), POKER("Poker"), STRAIGHT_FLUSH("Straight Flush"),
    ROYAL_FLUSH("Royal Flush");

    private final String str;

    private HandRank(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

}

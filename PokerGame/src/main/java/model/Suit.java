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
public enum Suit {
    CLUBS("Clubs"), DIAMONDS("Diamonds"), HEARTS("Hearts"), SPADES("Spades");

    private String s;

    private Suit(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}

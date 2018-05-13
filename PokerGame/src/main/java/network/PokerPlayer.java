/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import model.Card;

/**
 *
 * @author iron2414
 */
public class PokerPlayer {
    private String name;
    private int money;
    private Card[] hand;
    
    public PokerPlayer(String name, int money, Card[] hand)
    {
        this.name = name;
        this.money = money;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }
    
    
    
    
}

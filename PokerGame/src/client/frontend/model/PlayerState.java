package client.frontend.model;

import model.Card;

public class PlayerState {
    public String name;
    public int money;
    /**
     * Empty array means hand is not revealed.
     * It should have cards only after showdown, and for currentPlayer.
     */
    public Card[] hand;
    public boolean isDealer;
    public boolean isBigBlind;
    public boolean isSmallBlind;

    public PlayerState(String name, int money, Card[] hand, boolean isDealer, boolean isBigBlind, boolean isSmallBlind) {
        this.name = name;
        this.money = money;
        this.hand = hand;
        this.isDealer = isDealer;
        this.isBigBlind = isBigBlind;
        this.isSmallBlind = isSmallBlind;
    }
}

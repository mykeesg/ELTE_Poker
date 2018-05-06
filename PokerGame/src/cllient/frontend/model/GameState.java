package cllient.frontend.model;

import model.Card;

public class GameState {
    public PlayerState currentPlayer;
    public PlayerState[] opponents;
    public Card[] tableCards;
    public int pot;
    public int maxRaise;
    public boolean canFold;
    public boolean canCall;
    public boolean canRaise;

    public GameState(PlayerState currentPlayer, PlayerState[] opponents, Card[] tableCards, int pot, int maxRaise, boolean canFold, boolean canCall, boolean canRaise) {
        this.currentPlayer = currentPlayer;
        this.opponents = opponents;
        this.tableCards = tableCards;
        this.pot = pot;
        this.maxRaise = maxRaise;
        this.canFold = canFold;
        this.canCall = canCall;
        this.canRaise = canRaise;
    }
}

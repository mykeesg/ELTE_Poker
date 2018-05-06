package cllient.frontend.model;

import model.Card;

public class GameState {
    public PlayerState currentPlayer;
    public PlayerState[] opponents;
    public Card[] tableCards;
    public int pot;

    public GameState(PlayerState currentPlayer, PlayerState[] opponents, Card[] tableCards, int pot) {
        this.currentPlayer = currentPlayer;
        this.opponents = opponents;
        this.tableCards = tableCards;
        this.pot = pot;
    }
}

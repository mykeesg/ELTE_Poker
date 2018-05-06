package cllient.frontend.model;

import model.Card;

public class GameState {
    public PlayerState currentPlayer;
    public PlayerState[] opponents;
    public Card[] deskCards;
    public int pot;

    public GameState(PlayerState currentPlayer, PlayerState[] opponents, Card[] deskCards, int pot) {
        this.currentPlayer = currentPlayer;
        this.opponents = opponents;
        this.deskCards = deskCards;
        this.pot = pot;
    }
}

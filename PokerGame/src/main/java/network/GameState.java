package network;

import java.util.ArrayList;
import java.util.List;
import model.Card;

public class GameState {

    public PlayerState currentPlayer;
    public ArrayList<PlayerState> opponents;
    public List<Card> tableCards;
    public int pot;
    public int maxRaise;
    public boolean canFold;
    public boolean canCall;
    public boolean canRaise;
    public String winnerName;
    public String currentTurnPlayerName;

    public GameState(PlayerState currentPlayer, ArrayList<PlayerState> opponents, List<Card> tableCards, int pot, int maxRaise, boolean canFold, boolean canCall,
            boolean canRaise, String winnerName, String currentTurnPlayerName) {
        this.currentPlayer = currentPlayer;
        this.opponents = opponents;
        this.tableCards = tableCards;
        this.pot = pot;
        this.maxRaise = maxRaise;
        this.canFold = canFold;
        this.canCall = canCall;
        this.canRaise = canRaise;
        this.winnerName = winnerName;
        this.currentTurnPlayerName = currentTurnPlayerName;
    }
}

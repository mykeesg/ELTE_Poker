/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.util.List;
import model.Card;
import model.Player;

/**
 *
 * @author iron2414
 */
public class PokerState {

    private List<PokerPlayer> playerList;
    private List<Card> table;

    public PokerState(List<PokerPlayer> playerList, List<Card> table) {
        this.playerList = playerList;
        this.table = table;
    }

    public List<PokerPlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PokerPlayer> playerList) {
        this.playerList = playerList;
    }

    public List<Card> getTable() {
        return table;
    }

    public void setTable(List<Card> table) {
        this.table = table;
    }
}

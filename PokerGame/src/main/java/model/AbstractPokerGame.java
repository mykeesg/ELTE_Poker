/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

import utils.Pair;

/**
 *
 * @author mykee
 */
public interface AbstractPokerGame {

    public void newRound();

    public boolean isGameOver();
    
    public boolean isRoundOver();

    public Player getWinner();

    public List<Pair<Player, Result>> getFinalRanks();

    public void takeAction(int playerID, GameAction action, int money);

    public List<Card> getTableCards();

    public int getCurrentPlayerID();

    public int getPot();

    public int getDealerID();

    public int getSmallBlindID();

    public int getBigBlindID();

}

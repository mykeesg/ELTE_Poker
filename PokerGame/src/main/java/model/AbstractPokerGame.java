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

    void newRound();

    boolean isGameOver();

    boolean isRoundOver();

    Player getWinner();

    List<Pair<Player, Result>> getFinalRanks();

    void takeAction(int playerID, GameAction action, int money);

    List<Card> getTableCards();

    int getCurrentPlayerID();

    GameAction getLastAction();

    int getLastRaiseAmount();

    int getPot();

    int getDealerID();

    int getSmallBlindID();

    int getBigBlindID();

}

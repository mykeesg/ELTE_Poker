/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;

import utils.Pair;

/**
 *
 * @author mykee
 */
public class PokerGame implements Runnable {

    private final Deck deck;

    private final List<Player> players;
    private final List<Card> tableCards;
    private int minimumBet;

    private int pot;

    protected void dealForPlayers() {
        for (Player p : players) {
            Card[] hand = {deck.getTopCard(), deck.getTopCard()};
            p.setHand(hand);
        }
    }

    @Override
    public void run() {
        deck.shuffle();
        tableCards.clear();
        dealForPlayers();

        dealFlop();
        dealTurn();
        dealRiver();

        List<Pair<Player, Result>> finalRanks = new LinkedList<>();
        players.forEach((p) -> {
            Card[] cards = new Card[7];
            cards = tableCards.toArray(cards);
            cards[5] = p.getHand()[0];
            cards[6] = p.getHand()[1];

            Result r = Evaluator.getResult(cards);
            finalRanks.add(new Pair(p, r));
        });

        finalRanks.sort((a, b) -> {
            return b.getSecond().compareTo(a.getSecond());
        });
        
        System.out.println("First place: " + finalRanks.get(0));
        System.out.println("Second place: " + finalRanks.get(1));
        System.out.println("Third place: " + finalRanks.get(2));
    }

    public void dealFlop() {
        tableCards.add(deck.getTopCard());
        tableCards.add(deck.getTopCard());
        tableCards.add(deck.getTopCard());
    }

    public void dealTurn() {
        tableCards.add(deck.getTopCard());
    }

    public void dealRiver() {
        tableCards.add(deck.getTopCard());
    }

    public void addBet(int b) {
        pot += b;
    }

    public PokerGame(List<Player> players, int minimumBet) {
        this.players = players;
        this.minimumBet = minimumBet;
        tableCards = new LinkedList<>();
        deck = new Deck();
    }

    public static void main(String[] args) {

        List<Player> plist = new LinkedList<>();
//        plist.add(new Player("Joco", null, null));
//        plist.add(new Player("Bela", null, null));
//        plist.add(new Player("Gazsi", null, null));
//        plist.add(new Player("Tilda", null, null));

        PokerGame game = new PokerGame(plist, 100);

        game.run();
    }

    //TODO implements
    public boolean finished() {
        return false;
    }

}

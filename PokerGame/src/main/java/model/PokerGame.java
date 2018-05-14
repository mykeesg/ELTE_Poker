/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utils.Logger;
import utils.Pair;

/**
 *
 * @author mykee
 */
public class PokerGame implements AbstractPokerGame {

    public static void main(String[] args) throws Exception {

        List<Player> plist = new LinkedList<>();
        plist.add(new Player("Joco", null, null, null));
        plist.add(new Player("Bela", null, null, null));
        plist.add(new Player("Gazsi", null, null, null));
        plist.add(new Player("Tilda", null, null, null));

        plist.forEach(p -> p.setMoney(1000));

        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to enable LOG messages? (y/n)");
        Logger.LOGGING = (input.nextLine().equalsIgnoreCase("y"));

        String newRound = "y";
        AbstractPokerGame game = new PokerGame(plist, 100);
        do {
            game.newRound();

            Logger.logMessage("Dealer: " + plist.get(game.getDealerID()));
            Logger.logMessage("Small blind: " + plist.get(game.getSmallBlindID()));
            Logger.logMessage("Big blind: " + plist.get(game.getBigBlindID()));

            while (!game.isRoundOver()) {
                System.out.println("------");
                System.out.println("Current turn is on: " + plist.get(game.getCurrentPlayerID()).getName());
                System.out.println("\t his/her hand: " + Arrays.toString(plist.get(game.getCurrentPlayerID()).getHand()));
                System.out.println("Press 1 to Fold, 2 to Check/call, 3 to Raise");

                GameAction act = GameAction.get(Integer.parseInt(input.nextLine()) - 1);
                int money = 0;

                if (act == GameAction.RAISE) {
                    System.out.println("Enter the amount to raise: ");
                    money = Integer.parseInt(input.nextLine());
                }

                game.takeAction(game.getCurrentPlayerID(), act, money);

                //so the logs won't collide
                Thread.sleep(100);
            }
            System.out.println("Do you want to start a new round?  (y/n)");
            newRound = input.nextLine();
        } while (newRound.equalsIgnoreCase("y") && !game.isGameOver());
        System.out.println("game over");
    }

    private final Deck deck;

    private final List<PokerPlayer> players;
    private List<Pair<Player, Result>> finalRanks;

    private final List<Card> tableCards;
    private final int minimumBet;

    private boolean roundOver;

    private int pot;

    private Round currentRound;
    private GameAction lastAction;

    private int currentPlayerID;
    private int dealerID;
    private int smallBlindID;
    private int bigBlindID;

    private int lastRaiseAmount;

    public PokerGame(List<Player> playerList, int minimumBet) {
        if (playerList.size() < 2) {
            throw new IllegalStateException("You can't start a game without at least 2 players!");
        }

        if (minimumBet <= 0) {
            throw new IllegalStateException("The initial bet should be positive!!");
        }

        this.players = new LinkedList<>();
        finalRanks = new LinkedList<>();
        playerList.forEach((p) -> {
            players.add(new PokerPlayer(p));
        });

        this.minimumBet = minimumBet;

        currentPlayerID = 0;

        dealerID = players.size() - 1;
        bigBlindID = (dealerID + 2) % players.size();

        tableCards = new LinkedList<>();
        deck = new Deck();
    }

    private void dealForPlayers() {
        players.forEach((p) -> {
            if (p.isPlaying) {
                Card[] hand = {deck.getTopCard(), deck.getTopCard()};
                p.player.setHand(hand);
            }
        });
    }

    @Override
    public List<Card> getTableCards() {
        return tableCards;
    }

    @Override
    public int getCurrentPlayerID() {
        return currentPlayerID;
    }

    @Override
    public int getPot() {
        return pot;
    }

    @Override
    public int getDealerID() {
        return dealerID;
    }

    @Override
    public int getSmallBlindID() {
        return smallBlindID;
    }

    @Override
    public int getBigBlindID() {
        return bigBlindID;
    }

    @Override
    public List<Pair<Player, Result>> getFinalRanks() {
        return finalRanks;
    }

    @Override
    public boolean isRoundOver() {
        return roundOver;
    }

    private PokerPlayer smallBlindPlayer() {
        return players.get(smallBlindID);
    }

    private PokerPlayer bigBlindPlayer() {
        return players.get(bigBlindID);
    }

    private int getNextPlayerFrom(int current) {
        int nextID = (current + 1) % players.size();

        while (!players.get(nextID).isPlaying) {
            nextID = (nextID + 1) % players.size();
        }
        return nextID;
    }

    private int getPrevPlayerFrom(int current) {
        int prevID = current != 0 ? (current - 1) : players.size() - 1;
        while (!players.get(prevID).isPlaying) {
            prevID = prevID != 0 ? (prevID - 1) : players.size() - 1;
        }
        return prevID;
    }

    private int getPlayersStillInGame() {
        return (int) players.stream().filter(p -> p.isPlaying).count();
    }

    private void updateDealerID() {
        dealerID = getPrevPlayerFrom(smallBlindID);
    }

    private void updateSmallBlind() {
        smallBlindID = getPrevPlayerFrom(bigBlindID);
    }

    private void updateBigBlind() {
        bigBlindID = getNextPlayerFrom(bigBlindID);
    }

    private void updateRoles() {
        updateBigBlind();
        updateSmallBlind();
        updateDealerID();
    }

    @Override
    public void newRound() {

        Logger.logMessage("\n................................................");
        Logger.logMessage("\nA new round has started, ladies and gentlemen, place your bets!");

        deck.shuffle();
        tableCards.clear();
        finalRanks.clear();

        currentRound = Round.PREFLOP;

        players.forEach(p -> p.reset());

        updateRoles();

        smallBlindPlayer().player.setMoney(smallBlindPlayer().player.getMoney() - minimumBet / 2);

        bigBlindPlayer().player.setMoney(bigBlindPlayer().player.getMoney() - minimumBet);
        bigBlindPlayer().hasPlacedBet = true;

        lastRaiseAmount = minimumBet;
        lastAction = GameAction.RAISE;

        pot = minimumBet + minimumBet / 2;

        dealForPlayers();

        currentPlayerID = getNextPlayerFrom(bigBlindID);
    }

    @Override
    public boolean isGameOver() {
        return players.stream().filter(p -> p.isPlaying).count() <= 1;
    }

    @Override
    public Player getWinner() {
        for (PokerPlayer p : players) {
            if (p.isPlaying) {
                return p.player;
            }
        }
        return null;
    }

    @Override
    public void takeAction(int playerID, GameAction action, int money) {
        PokerPlayer pokerPlayer = players.get(playerID);

        if (playerID == currentPlayerID) {
            if (!pokerPlayer.isPlaying) {
                throw new IllegalActionException("This player is not even playing this round!");
            }

            if (null == action) {
                throw new IllegalStateException("game shouldn't reach this");
            } else /*
            if (lastAction == GameAction.RAISE && action == GameAction.CHECK_OR_CALL) {
            if (pokerPlayer.player.getMoney() > 0) {
            throw new IllegalActionException("Player can't check if there was a raise before!");
            } else {
            //he's in All-In
            pokerPlayer.hasPlacedBet = true;
            }
            } else
             */ {
                switch (action) {
                    case FOLD:
                        pokerPlayer.isPlaying = false;
                        break;
                    case RAISE:
                        if (lastAction == GameAction.CHECK_OR_CALL) {
                            players.forEach(p -> {
                                if (p.isPlaying && p != pokerPlayer) {
                                    p.hasPlacedBet = false;
                                }
                            });
                            lastAction = GameAction.RAISE;
                            lastRaiseAmount = money;
                        }
                        if (lastRaiseAmount > 0 && money < lastRaiseAmount && pokerPlayer.player.getMoney() >= lastRaiseAmount) {
                            throw new IllegalArgumentException("Player can't raise less, than $" + lastRaiseAmount + " if he's not 'All-in'!");
                        } else {
                            if (money < minimumBet && pokerPlayer.player.getMoney() >= minimumBet) {
                                throw new IllegalArgumentException("Player can't raise less, than $" + minimumBet + " if he's not 'All-in'!");
                            }
                            pokerPlayer.hasPlacedBet = true;
                            pokerPlayer.player.modifyMoney(-money);
                            pot += money;
                        }
                        break;
                    case CHECK_OR_CALL:
                        //he check/called
                        pokerPlayer.hasPlacedBet = true;
                        if (lastAction == GameAction.RAISE) {
                            int playerMoney = pokerPlayer.player.getMoney();
                            if (playerMoney < lastRaiseAmount) {
                                pot += playerMoney;
                                pokerPlayer.player.setMoney(0);
                            } else {
                                pot += lastRaiseAmount;
                                pokerPlayer.player.modifyMoney(-lastRaiseAmount);
                            }
                        }
                        break;
                    default:
                        throw new IllegalStateException("game shouldn't reach this");
                }
            }
            currentPlayerID = getNextPlayerFrom(currentPlayerID);
        } else {
            throw new IllegalActionException("It's not this player's round!");
        }

        checkState();
    }

    private int didAction() {
        return (int) players.stream().filter(p -> p.hasPlacedBet).count();
    }

    private void checkState() {
        if (getPlayersStillInGame() == 1) {
            Logger.logMessage("Everyone else has fold, the winner is " + getWinner().getName() + "(won $" + pot + ").");
            getWinner().modifyMoney(pot);
            roundOver = true;
            return;
        }

        if (didAction() == getPlayersStillInGame()) {
            lastAction = GameAction.CHECK_OR_CALL;
            lastRaiseAmount = 0;

            switch (currentRound) {
                case PREFLOP:
                    dealFlop();
                    break;
                case FLOP:
                    dealTurn();
                    break;
                case TURN:
                    dealRiver();
                    break;
                case RIVER:
                    showResult();
                    roundOver = true;
                    return;
            }
            currentRound = currentRound.advance();
            players.forEach(p -> p.hasPlacedBet = false);

            Logger.logMessage("\n................................................");
            Logger.logMessage("Table after " + currentRound.name());
            tableCards.forEach(c -> Logger.logString(c.toString()));
            Logger.logMessage("\nThere's $" + pot + " in the pot.");
            Logger.logMessage("\n................................................");
            Logger.logMessage("Players still in the game: ");
            players.stream().filter(p -> p.isPlaying).forEach(p -> Logger.logMessage(p.player.getName()));
            Logger.logMessage("\n................................................");
            currentPlayerID = getNextPlayerFrom(dealerID);
        }
    }

    private void showResult() {

        players.forEach((p) -> {
            if (p.isPlaying) {
                Card[] cards = new Card[7];
                cards = tableCards.toArray(cards);
                cards[5] = p.player.getHand()[0];
                cards[6] = p.player.getHand()[1];

                Result r = Evaluator.getResult(cards);
                finalRanks.add(new Pair(p.player, r));
            }
        });

        finalRanks.sort((a, b) -> {
            return b.getSecond().compareTo(a.getSecond());
        });

        Logger.logMessage("\n................................................");
        Logger.logMessage("\nLadies and gentlemen, this round is over!");
        for (int i = 0; i < finalRanks.size(); ++i) {
            Logger.logMessage(String.format("%d. place: %s (with %s)", i + 1, finalRanks.get(i).getFirst().getName(), finalRanks.get(i).getSecond().rank));
        }

        Logger.logMessage("\n................................................");
        finalRanks.get(0).getFirst().modifyMoney(pot);
        players.forEach(p -> p.isPlaying = false);
    }

    private void dealFlop() {
        tableCards.add(deck.getTopCard());
        tableCards.add(deck.getTopCard());
        tableCards.add(deck.getTopCard());
    }

    private void dealTurn() {
        tableCards.add(deck.getTopCard());
    }

    private void dealRiver() {
        tableCards.add(deck.getTopCard());
    }

    private enum Round {
        PREFLOP, FLOP, TURN, RIVER;

        public Round advance() {
            if (this != RIVER) {
                return values()[this.ordinal() + 1];
            } else {
                return this;
            }
        }
    }

    private class PokerPlayer {

        public Player player;
        public boolean isPlaying;
        public boolean hasPlacedBet;

        public PokerPlayer(Player player) {
            this.player = player;
            isPlaying = true;
            hasPlacedBet = false;
        }

        public void reset() {
            isPlaying = player.getMoney() >= PokerGame.this.minimumBet;
            hasPlacedBet = false;
            player.setHand(null);
        }

    }

}

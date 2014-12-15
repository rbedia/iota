package org.doxu.iota;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class Game {

    private final Board board;

    private final Deck deck;

    private final List<Player> players;

    private boolean gameover;

    private int currentPlayer;

    private int passCount;

    public Game() {
        players = new ArrayList<>();
        board = new Board();
        deck = new Deck();
        init(players);
    }

    public final void init(List<Player> newPlayers) {
        gameover = false;
        currentPlayer = 0;
        passCount = 0;
        players.clear();
        players.addAll(newPlayers);
        int index = 1;
        for (Player player : players) {
            PlayerFactory.init(player, index, board);
            index++;
        }
        board.init();
        deck.init();
    }

    public static class PlayerFactory {

        public static void init(Player player, int index, Board board) {
            player.setHand(new Hand());
            player.setBoard(board);
            player.setIndex(index);
        }
    }

    public void deal() {
        for (int i = 0; i < Hand.MAX_HAND; i++) {
            for (Player player : players) {
                player.getHand().add(deck.draw());
            }
        }
    }

    public void deal(Player player) {
        Hand hand = player.getHand();
        while (!hand.isFullHand() && deck.hasCards()) {
            hand.add(deck.draw());
        }
    }

    public void playStartingCard() {
        Card first = deck.draw();
        board.addFirst(first);
    }

    /**
     * Executes one player's turn.
     *
     * @return True if the game is over, false if the game continues.
     */
    public boolean step() {
        if (gameover) {
            return true;
        }
        Player player = players.get(currentPlayer % players.size());
        Laydown laydown = player.turn();
        if (laydown == null) {
            pass(player);
        } else {
            laydown(laydown, player);
            passCount = 0;
        }
        if (passCount == players.size()) {
            System.out.println("Everyone passed. Game over.");
            gameover = true;
        }
        if (isEndOfRound()) {
            printEndOfRound();
        }
        currentPlayer++;
        return gameover;
    }

    public void pass(Player player) {
        System.out.println("Player " + player.getDisplayName() + " is passing.");
        passCount++;
    }

    private boolean isEndOfRound() {
        return currentPlayer % players.size() == players.size() - 1;
    }

    private void printEndOfRound() {
        int round = getRound();
        System.out.println("End of round " + round + ":");
        for (Player player : players) {
            System.out.println("   Player " + player.getDisplayName() + ": " + player.getScore());
        }
        board.print();
    }

    private int getRound() {
        return (int) Math.floor((currentPlayer + 1) / players.size());
    }

    private void laydown(Laydown laydown, Player player) {
        try {
            // TODO verify that all cards in laydown came from player's hand
            int score = board.applyLaydown(laydown);
            player.getHand().remove(laydown.getCards());
            deal(player);
            if (player.getHand().isEmpty()) {
                System.out.println("Player " + player.getDisplayName() + " went out. Score doubled.");
                score *= 2;
                gameover = true;
            }
            player.addScore(score);
        } catch (IllegalLaydownException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printDeck() {
        for (Card card : deck.getCards()) {
            System.out.println(card);
        }
    }

    public void printHands() {
        for (Player player : players) {
            System.out.print("Player " + player.getDisplayName() + " Hand:");
            for (Card card : player.getHand().getCards()) {
                System.out.print(" " + card);
            }
            System.out.println();
        }
    }

    public void printBoard() {
        board.print();
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }
}

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

    public Game() {
        players = new ArrayList<>();
        board = new Board();
        deck = new Deck();
        init(players);
    }

    public final void init(List<Player> newPlayers) {
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

    private void deal(Player player) {
        Hand hand = player.getHand();
        while (!hand.isFullHand() && deck.hasCards()) {
            hand.add(deck.draw());
        }
    }

    public void playStartingCard() {
        Card first = deck.draw();
        board.addFirst(first);
    }

    public void play(PlayListener listener) {
        boolean play = true;
        int round = 0;
        while (play) {
            int passCount = 0;
            round++;
            for (Player player : players) {
                if (player.getHand().getCards().isEmpty()) {
                    play = false;
                    break;
                }
                Laydown laydown = player.turn();
                if (laydown == null) {
                    System.out.println("Player " + player.getDisplayName() + " is passing.");
                    passCount++;
                } else {
                    try {
                        // TODO verify that all cards in laydown came from player's hand
                        int score = board.applyLaydown(laydown);
                        player.getHand().remove(laydown.getCards());
                        deal(player);
                        if (player.getHand().isEmpty()) {
                            System.out.println("Player " + player.getDisplayName() + " went out. Score doubled.");
                            score *= 2;
                            play = false;
                        }
                        player.addScore(score);
                    } catch (IllegalLaydownException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                listener.turn(player, laydown);
                if (!play) {
                    break;
                }
            }
            if (passCount == players.size()) {
                System.out.println("Everyone passed. Game over.");
                play = false;
            }
            System.out.println("End of round " + round + ":");
            for (Player player : players) {
                System.out.println("   Player " + player.getDisplayName() + ": " + player.getScore());
            }
            board.print();
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
}

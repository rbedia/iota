package org.doxu.iota;

import java.util.List;

/**
 *
 * @author rafael
 */
public class Game {

    private final Board board;

    private final Deck deck;

    private final List<Player> players;

    public Game(List<Player> players) {
        board = new Board();
        deck = new Deck();
        this.players = players;
        int index = 1;
        for (Player player : players) {
            PlayerFactory.init(player, index, board);
            index++;
        }
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

    public void play() {
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
                    System.out.println("Player is passing.");
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
                            player.addScore(score);
                            play = false;
                            break;
                        }
                        player.addScore(score);
                    } catch (IllegalLaydownException ex) {
                        System.out.println(ex.getMessage());
                    }
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
}

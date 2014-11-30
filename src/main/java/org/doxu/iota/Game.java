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
        for (Player player : players) {
            PlayerFactory.init(player, board);
        }
    }

    public static class PlayerFactory {

        public static void init(Player player, Board board) {
            player.setHand(new Hand());
            player.setBoard(board);
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
                        player.addScore(board.applyLaydown(laydown));
                        player.getHand().remove(laydown.getCards());
                        deal(player);
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
                System.out.println("   Player " + player.getName() + ": " + player.getScore());
            }
            board.print();
        }
    }

//    public void playRandom() {
//        Random random = new Random();
//        while (!players.get(0).getCards().isEmpty()) {
//            for (Hand hand : players) {
//                Card card1 = hand.getCards().get(0);
//                hand.remove(card1);
//                boolean success = false;
//                while (!success) {
//                    Laydown laydown = new Laydown();
//                    laydown.addMove(new Move(new Location(random.nextInt(10) + 60, random.nextInt(10) + 60), card1));
//                    try {
//                        board.applyLaydown(laydown);
//                        success = true;
//                    } catch (IllegalLaydownException ex) {
//                        System.out.println(ex.getMessage());
//                        printBoard();
////                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }
//    }
    public void printDeck() {
        for (Card card : deck.getCards()) {
            System.out.println(card);
        }
    }

    public void printHands() {
        for (Player player : players) {
            System.out.print("Hand:");
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

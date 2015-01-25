package org.doxu.iota;

import org.doxu.iota.board.Board;
import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.player.ScoreLaydown;
import org.doxu.iota.turn.Turn;

public class Game {

    private final Board board;

    private final Deck deck;

    private final List<Player> players;

    private boolean gameover;

    private int currentPlayer;

    private int passCount;

    private GameLog gameLog;

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
            PlayerFactory.init(player, index, board, deck);
            index++;
        }
        board.init();
        deck.init();
        gameLog = new GameLog(players.size());
    }

    public static class PlayerFactory {

        public static void init(Player player, int index, Board board, Deck deck) {
            player.setBoard(board);
            player.setIndex(index);
            player.setDeck(deck);
            player.getHand().clear();
            player.resetScore();
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

    public void pass(Player player) {
        System.out.println("Player " + player.getDisplayName() + " is passing.");
        passCount++;
    }

    public void resetPassCount() {
        passCount = 0;
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
        Player player = getCurrentPlayer();
        Turn turn = player.turn();
        ScoreLaydown scoreLog = turn.execute(this, player);
        gameLog.addLaydown(scoreLog);
        if (passCount == players.size()) {
            System.out.println("Everyone passed. Game over.");
            gameover = true;
        }
        // Fail safe to prevent getting stuck in a loop.
        if (isEndOfRound() && getRound() == 50) {
            gameover = true;
        }
        currentPlayer++;
        return gameover;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer % players.size());
    }

    public boolean isEndOfRound() {
        return currentPlayer % players.size() == players.size() - 1;
    }

    public Player getWinner() {
        Player winner = players.get(0);
        int bestScore = winner.getScore();
        for (int i = 1; i < players.size(); i++) {
            Player player = players.get(i);
            // XXX how to handle ties?
            if (player.getScore() > bestScore) {
                winner = player;
                bestScore = player.getScore();
            }
        }
        return winner;
    }

    public void printEndOfRound() {
        int round = getRound();
        System.out.println("End of round " + round + ":");
        for (Player player : players) {
            System.out.println("   Player " + player.getDisplayName() + ": " + player.getScore());
        }
        board.print();
    }

    private int getRound() {
        return (int) Math.floor((currentPlayer + 1) / (double) players.size());
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

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    public GameLog getGameLog() {
        return gameLog;
    }
}

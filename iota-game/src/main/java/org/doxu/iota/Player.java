package org.doxu.iota;

import org.doxu.iota.board.Board;
import org.doxu.iota.turn.Turn;

public abstract class Player {

    private String name;
    private int index;
    private final boolean human;
    private final Hand hand;
    private Board board;
    private Deck deck;
    private int score;

    public Player(boolean human) {
        name = "";
        hand = new Hand();
        this.human = human;
    }

    public abstract Turn turn();

    public Hand getHand() {
        return hand;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void addScore(int moveScore) {
        score += moveScore;
    }

    public void resetScore() {
        score = 0;
    }

    public String getDisplayName() {
        return index + " " + name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHuman() {
        return human;
    }
}

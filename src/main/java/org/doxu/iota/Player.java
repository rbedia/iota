package org.doxu.iota;

import org.doxu.iota.turn.Turn;

public abstract class Player {

    private String name;
    private int index;
    private Hand hand;
    private Board board;
    private int score;

    public Player() {
        name = "";
    }

    public abstract Turn turn();

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addScore(int moveScore) {
        score += moveScore;
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

}

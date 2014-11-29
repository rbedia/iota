package org.doxu.iota;

/**
 *
 * @author rafael
 */
public abstract class Player {

    private String name;
    private Hand hand;
    private Board board;
    private int score;

    public abstract Laydown turn();

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

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

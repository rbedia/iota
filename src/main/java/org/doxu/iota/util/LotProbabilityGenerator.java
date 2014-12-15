package org.doxu.iota.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.doxu.iota.Board;
import org.doxu.iota.Card;
import org.doxu.iota.Deck;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;

/**
 * This utility figures out how many cards are valid to go into a row that
 * already has two cards in it. This can be used to calculate the probability of
 * getting a card that will play in that particular location.
 */
public class LotProbabilityGenerator {

    public static void main(String[] args) {
        LotProbabilityGenerator gen = new LotProbabilityGenerator();
        int count = gen.test("R+1", "R+2");
        System.out.println("1 attribute different: " + count + " options");
        count = gen.test("R+1", "RO2");
        System.out.println("2 attributes different: " + count + " options");
        count = gen.test("R+1", "GO2");
        System.out.println("3 attributes different: " + count + " options");
    }

    private int test(String card1, String card2) {
        Board board = new Board();
        board.addFirst(Card.create(card1));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create(card2)));
        try {
            board.applyLaydown(laydown);
        } catch (IllegalLaydownException ex) {
            Logger.getLogger(LotProbabilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        Deck deck = new Deck();
        int count = 0;
        while (deck.hasCards()) {
            try {
                Card card = deck.draw();
                Laydown trialLaydown = new Laydown();
                trialLaydown.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE), card));
                Board trialBoard = board.copy();
                trialBoard.applyLaydown(trialLaydown);
                System.out.println(card);
                count++;
            } catch (IllegalLaydownException ex) {
            }

        }
        return count;
    }
}

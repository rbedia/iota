package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import org.doxu.iota.Board;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.PassTurn;
import org.doxu.iota.turn.Turn;

/**
 *
 * @author rafael
 */
public class SimpleHighPlayer extends Player {

    private final Random random = new Random();

    private class ScoreLaydown implements Comparable<ScoreLaydown> {

        int score;
        Laydown laydown;

        public ScoreLaydown(int score, Laydown laydown) {
            this.score = score;
            this.laydown = laydown;
        }

        @Override
        public int compareTo(ScoreLaydown o) {
            return o.score == score ? 0 : o.score < score ? -1 : 1;
        }
    }

    public SimpleHighPlayer() {
        setName("simple high");
    }

    @Override
    public Turn turn() {
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        for (Card card : getHand().getCards()) {
            List<Location> locations = collectValidLocations();
            while (!locations.isEmpty()) {
                Location location = locations.remove(random.nextInt(locations.size()));
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    int score = getBoard().copy().applyLaydown(laydown);
                    options.add(new ScoreLaydown(score, laydown));
                } catch (IllegalLaydownException ex) {
                }
            }
        }
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown, this);
        }
        return new PassTurn(this);
    }

    private List<Location> collectValidLocations() {
        List<Location> locations = new ArrayList<>();
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                Location location = new Location(x, y);
                if (!getBoard().isOverlappingCard(location) && getBoard().isTouchingBoard(location)) {
                    locations.add(location);
                }
            }
        }
        return locations;
    }
}

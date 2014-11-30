package org.doxu.iota;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

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

    @Override
    public Laydown turn() {
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
//                    getBoard().validLaydown(laydown);
//                    return laydown;
                } catch (IllegalLaydownException ex) {
//                    System.out.println(ex.getMessage());
//                    getBoard().print();
//                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return scoreLaydown.laydown;
        }
        return null;
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

package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.Board;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.PassTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighTwoPlayer extends Player {

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

    public SimpleHighTwoPlayer() {
        setName("simple high two");
    }

    @Override
    public Turn turn() {
        List<ScoreLaydown> options1 = new ArrayList<>();
        for (Card card : getHand().getCards()) {
            List<Location> locations = collectValidLocations();
            while (!locations.isEmpty()) {
                Location location = locations.remove(0);
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    int score = getBoard().copy().applyLaydown(laydown);
                    options1.add(new ScoreLaydown(score, laydown));
                } catch (IllegalLaydownException ex) {
                }
            }
        }
        List<ScoreLaydown> options2 = new ArrayList<>();
        for (ScoreLaydown scoreLaydown : options1) {
            for (Card card : getHand().getCards()) {
                if (card != scoreLaydown.laydown.getCards().get(0)) {
                    List<Location> locations = collectValidLocations(scoreLaydown.laydown.getMoves().get(0).getLocation());
                    for (Location location : locations) {
                        Laydown laydown = scoreLaydown.laydown.copy();
                        laydown.addMove(new Move(location, card));
                        try {
                            int score = getBoard().copy().applyLaydown(laydown);
                            options2.add(new ScoreLaydown(score, laydown));
                        } catch (IllegalLaydownException ex) {
                        }
                    }
                }
            }
        }
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        options.addAll(options1);
        options.addAll(options2);
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

    private List<Location> collectValidLocations(Location startingLocation) {
        List<Location> locations = new ArrayList<>();
        // Search left
        Location location = startingLocation.moveLeft();
        while (getBoard().isOverlappingCard(location)) {
            location = location.moveLeft();
        }
        locations.add(location);
        // Search right
        location = startingLocation.moveRight();
        while (getBoard().isOverlappingCard(location)) {
            location = location.moveRight();
        }
        locations.add(location);
        // Search up
        location = startingLocation.moveUp();
        while (getBoard().isOverlappingCard(location)) {
            location = location.moveUp();
        }
        locations.add(location);
        // Search down
        location = startingLocation.moveDown();
        while (getBoard().isOverlappingCard(location)) {
            location = location.moveDown();
        }
        locations.add(location);

        return locations;
    }
}

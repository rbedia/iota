package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.ArrayList;
import java.util.List;
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

public class RandomPlayer extends Player {

    private final Random random = new Random();

    public RandomPlayer() {
        setName("random");
    }

    @Override
    public Turn turn() {
        for (Card card : getHand().getCards()) {
            List<Location> locations = collectValidLocations();
            while (!locations.isEmpty()) {
                Location location = locations.remove(random.nextInt(locations.size()));
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    getBoard().validLaydown(laydown);
                    return new LaydownTurn(laydown, this);
                } catch (IllegalLaydownException ex) {
//                    System.out.println(ex.getMessage());
//                    getBoard().print();
//                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

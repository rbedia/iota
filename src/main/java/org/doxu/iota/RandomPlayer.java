package org.doxu.iota;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author rafael
 */
public class RandomPlayer extends Player {

    private final Random random = new Random();

    public RandomPlayer() {
        setName("random");
    }

    @Override
    public Laydown turn() {
        for (Card card : getHand().getCards()) {
            List<Location> locations = collectValidLocations();
            while (!locations.isEmpty()) {
                Location location = locations.remove(random.nextInt(locations.size()));
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    getBoard().validLaydown(laydown);
                    return laydown;
                } catch (IllegalLaydownException ex) {
//                    System.out.println(ex.getMessage());
//                    getBoard().print();
//                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

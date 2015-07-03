package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.List;
import java.util.Random;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class RandomPlayer extends Player {

    private final Random random = new Random();

    public RandomPlayer() {
        super(false);
        setName("random");
    }

    @Override
    public Turn turn() {
        for (Card card : getHand().getCards()) {
            List<Location> locations = SimpleHighCommon.collectValidLocations(getBoard());
            while (!locations.isEmpty()) {
                Location location = locations.remove(random.nextInt(locations.size()));
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    getBoard().validLaydown(laydown);
                    return new LaydownTurn(laydown);
                } catch (IllegalLaydownException ex) {
                }
            }
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

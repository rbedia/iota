package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighThreePlayer extends Player {

    public SimpleHighThreePlayer() {
        setName("simple high three");
    }

    @Override
    public Turn turn() {
        List<ScoreLaydown> options1 = new ArrayList<>();
        for (Card card : getHand().getCards()) {
            List<Location> locations = SimpleHighCommon.collectValidLocations(getBoard());
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
                    List<Location> locations = SimpleHighCommon.collectValidLocations(getBoard(), scoreLaydown.laydown.getMoves().get(0).getLocation());
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
        List<ScoreLaydown> options3 = new ArrayList<>();
        for (ScoreLaydown scoreLaydown : options2) {
            for (Card card : getHand().getCards()) {
                if (!scoreLaydown.laydown.getCards().contains(card)) {
                    Location location1 = scoreLaydown.laydown.getMoves().get(0).getLocation();
                    Location location2 = scoreLaydown.laydown.getMoves().get(1).getLocation();
                    List<Location> locations = SimpleHighCommon.collectValidLocations(getBoard(), location1, location2);
                    for (Location location : locations) {
                        Laydown laydown = scoreLaydown.laydown.copy();
                        laydown.addMove(new Move(location, card));
                        try {
                            int score = getBoard().copy().applyLaydown(laydown);
                            options3.add(new ScoreLaydown(score, laydown));
                        } catch (IllegalLaydownException ex) {
                        }
                    }
                }
            }
        }
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        options.addAll(options1);
        options.addAll(options2);
        options.addAll(options3);
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown, this);
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

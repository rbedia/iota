package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.board.Board;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighPlayer extends Player {

    public SimpleHighPlayer() {
        setName("simple high");
    }

    @Override
    public Turn turn() {
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        for (Card card : getHand().getCards()) {
            List<Location> locations = SimpleHighCommon.collectValidLocations(getBoard());
            for (Location location : locations) {
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    Board boardCopy = getBoard().overlay();
                    int score = boardCopy.applyLaydown(laydown);
                    options.add(new ScoreLaydown(score, laydown));
                } catch (IllegalLaydownException ex) {
                }
            }
        }
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown, this);
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

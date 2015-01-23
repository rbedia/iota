package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.Card;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighPlayer extends Player {

    public SimpleHighPlayer() {
        super(false);
        setName("simple high");
    }

    @Override
    public Turn turn() {
        List<Card> cards = getHand().getCards();
        List<ScoreLaydown> options1 = SimpleHighCommon.findOptions(cards, getBoard());
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        options.addAll(options1);
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown, this);
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

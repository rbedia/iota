package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.Card;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighThreePlayer extends Player {

    public SimpleHighThreePlayer() {
        super(false);
        setName("simple high three");
    }

    @Override
    public Turn turn() {
        List<Card> cards = getHand().getCards();
        List<ScoreLaydown> options1 = SimpleHighCommon.findOptions(cards, getBoard());
        List<ScoreLaydown> options2 = SimpleHighCommon.findOptions(cards, getBoard(), options1, false);
        List<ScoreLaydown> options3 = SimpleHighCommon.findOptions(cards, getBoard(), options2, false);
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        options.addAll(options1);
        options.addAll(options2);
        options.addAll(options3);
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown);
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

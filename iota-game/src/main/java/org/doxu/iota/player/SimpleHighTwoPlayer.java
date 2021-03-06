package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.Card;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighTwoPlayer extends Player {

    public SimpleHighTwoPlayer() {
        super(false);
        setName("simple high two");
    }

    @Override
    public Turn turn() {
        List<Card> cards = getHand().getCards();
        List<ScoreLaydown> options1 = SimpleHighCommon.findOptions(cards, getBoard());
        List<ScoreLaydown> options2 = SimpleHighCommon.findOptions(cards, getBoard(), options1, false);
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        options.addAll(options1);
        options.addAll(options2);
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown);
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

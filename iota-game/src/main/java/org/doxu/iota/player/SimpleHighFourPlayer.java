package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.List;
import java.util.PriorityQueue;
import org.doxu.iota.Card;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighFourPlayer extends Player {

    public SimpleHighFourPlayer() {
        super(false);
        setName("simple high four");
    }

    @Override
    public Turn turn() {
        List<Card> cards = getHand().getCards();
        List<ScoreLaydown> options1 = SimpleHighCommon.findOptions(cards, getBoard());
        List<ScoreLaydown> options2 = SimpleHighCommon.findOptions(cards, getBoard(), options1, false);
        List<ScoreLaydown> options3 = SimpleHighCommon.findOptions(cards, getBoard(), options2, false);
        // Score doubled for playing all cards in hand
        List<ScoreLaydown> options4 = SimpleHighCommon.findOptions(cards, getBoard(), options3, true);
        PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
        options.addAll(options1);
        options.addAll(options2);
        options.addAll(options3);
        options.addAll(options4);
        if (!options.isEmpty()) {
            ScoreLaydown scoreLaydown = options.remove();
            return new LaydownTurn(scoreLaydown.laydown);
        }
        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }
}

package org.doxu.iota.player;

import java.util.ArrayList;
import org.doxu.iota.Player;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.doxu.iota.Card;
import org.doxu.iota.Deck;
import org.doxu.iota.Location;
import org.doxu.iota.board.BoardBounds;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.Turn;

public class MinimizerPlayer extends Player {

    public MinimizerPlayer() {
        super(false);
        setName("minimizer");
    }

    @Override
    public Turn turn() {
        List<Card> deck = unplayedCards();

        List<ScoreLaydown> oppOptions1 = SimpleHighCommon.findOptions(deck, getBoard());
        PriorityQueue<ScoreLaydown> oppOptions = new PriorityQueue<>();
        oppOptions.addAll(oppOptions1);
        List<Card> cards = getHand().getCards();
        List<ScoreLaydown> options1 = SimpleHighCommon.findOptions(cards, getBoard());
        // For each of the opponent's highest scoring moves try to make a
        // maximum play in that location to block the opponent.
        while (!oppOptions.isEmpty()) {
            ScoreLaydown oppLaydown = oppOptions.remove();
            Location oppLocation = oppLaydown.laydown.getLocations().get(0);
            PriorityQueue<ScoreLaydown> options = new PriorityQueue<>();
            options.addAll(options1);
            while (!options.isEmpty()) {
                ScoreLaydown scoreLaydown = options.remove();
                if (scoreLaydown.laydown.contains(oppLocation)) {
                    return new LaydownTurn(scoreLaydown.laydown);
                }
            }
        }

        return SimpleHighCommon.basicTrade(getDeck(), getHand(), this);
    }

    /**
     * Find all cards still in the deck or in opponent hands.
     *
     * @return
     */
    private List<Card> unplayedCards() {
        Set<Card> deck = Deck.generateDeck();
        BoardBounds bounds = getBoard().getBounds();
        for (int i = bounds.getMinX(); i <= bounds.getMaxX(); i++) {
            for (int j = bounds.getMinY(); j <= bounds.getMaxY(); j++) {
                Card card = getBoard().getCard(i, j);
                deck.remove(card);
            }
        }
        for (Card card : getHand().getCards()) {
            deck.remove(card);
        }
        List<Card> cards = new ArrayList<>(deck.size());
        cards.addAll(deck);
        return cards;
    }
}

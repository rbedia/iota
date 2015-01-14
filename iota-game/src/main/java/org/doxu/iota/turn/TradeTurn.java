package org.doxu.iota.turn;

import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.Player;

public class TradeTurn extends BaseTurn {

    private final List<Card> cards;

    public TradeTurn(List<Card> cards, Player player) {
        super(player);
        this.cards = cards;
    }

    @Override
    public void execute() {
        System.out.println("Player " + player.getDisplayName() + " is trading " + cards.size() + " cards.");
        tradeCards(cards, player);
    }

    public void tradeCards(List<Card> cards, Player player) {
        setNoScoreLog();
        for (Card card : cards) {
            if (!player.getHand().getCards().contains(card)) {
                return;
            }
        }
        if (game.getDeck().count() >= cards.size()) {
            player.getHand().remove(cards);
            game.getDeck().addToBottom(cards);
            game.deal(player);
        }
    }

}

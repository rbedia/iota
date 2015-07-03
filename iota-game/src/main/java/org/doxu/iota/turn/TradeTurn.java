package org.doxu.iota.turn;

import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.Game;
import org.doxu.iota.Player;
import org.doxu.iota.player.ScoreLaydown;

public class TradeTurn extends BaseTurn {

    private final List<Card> cards;

    public TradeTurn(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public ScoreLaydown execute(Game game, Player player) {
        System.out.println("Player " + player.getDisplayName() + " is trading " + cards.size() + " cards.");
        // Check that the player has all of the cards they want to trade.
        for (Card card : cards) {
            if (!player.getHand().getCards().contains(card)) {
                return noScoreLaydown();
            }
        }
        if (game.getDeck().count() >= cards.size()) {
            player.getHand().remove(cards);
            game.getDeck().addToBottom(cards);
            game.deal(player);
        }
        return noScoreLaydown();
    }

}

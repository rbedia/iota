package org.doxu.iota.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import org.doxu.iota.Card;
import org.doxu.iota.Hand;
import org.doxu.iota.Player;

/**
 * Display for a list of cards.
 */
public class Rack extends JPanel {

    private final List<Card> cards;

    public Rack(List<Card> cards) {
        this.cards = cards;
        int count = Math.max(cards.size(), Hand.MAX_HAND);
        Dimension dim = new Dimension((CardRenderer.CARD_WIDTH + CardRenderer.INSET) * count, CardRenderer.CARD_WIDTH + 5);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }

    public Rack(Player player) {
        this(player.getHand().getCards());
    }

    private void draw(Graphics g) {
        for (int i = 0; i < cards.size(); i++) {
            Graphics g2card = g.create(i * CardRenderer.CARD_WIDTH, 0, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
            CardRenderer.draw(g2card, cards.get(i));
            g2card.dispose();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}

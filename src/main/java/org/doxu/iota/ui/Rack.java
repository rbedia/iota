package org.doxu.iota.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import org.doxu.iota.Card;
import org.doxu.iota.Player;

/**
 * Display for a player's available cards.
 */
public class Rack extends JPanel {

    private final Player player;

    public Rack(Player player) {
        this.player = player;
        Dimension dim = new Dimension((CardRenderer.CARD_WIDTH + CardRenderer.INSET) * 4, CardRenderer.CARD_WIDTH + 5);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }

    private void draw(Graphics g) {
        List<Card> cards = player.getHand().getCards();

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

package org.doxu.iota.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        Graphics2D g2d = (Graphics2D) g;

        List<Card> cards = player.getHand().getCards();

        for (int i = 0; i < cards.size(); i++) {
            CardRenderer.draw(g2d, cards.get(i), i * CardRenderer.CARD_WIDTH, 0);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}

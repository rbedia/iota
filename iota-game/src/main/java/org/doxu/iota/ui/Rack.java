package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import org.doxu.iota.Card;
import org.doxu.iota.Hand;
import org.doxu.iota.Player;

/**
 * Display for a list of cards.
 */
public class Rack extends JPanel implements CardListener {

    private static final Color SELECTED_CARD_COLOR = new Color(0, 0, 0);

    private final List<Card> cards;

    private Card selection;

    public Rack(final List<Card> cards) {
        this.cards = cards;
        selection = Card.BLANK;
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
            int x = i * CardRenderer.CARD_WIDTH;
            int y = 0;
            Graphics g2card = g.create(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
            Card card = cards.get(i);
            CardRenderer.draw(g2card, card);
            if (card.equals(selection)) {
                drawSelection(g2card);
            }
            g2card.dispose();
        }
    }

    private void drawSelection(Graphics g2card) {
        g2card.setColor(SELECTED_CARD_COLOR);
        g2card.drawRect(0, 0, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
        g2card.drawRect(1, 1, CardRenderer.CARD_WIDTH - 2, CardRenderer.CARD_WIDTH - 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void addCardListener() {
        addCardListener(this);
    }

    public void addCardListener(final CardListener cardListener) {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Point loc = e.getPoint();
                if (loc.y >= 0 && loc.y < CardRenderer.CARD_WIDTH) {
                    int cardIndex = (int) Math.floor((double) loc.x / CardRenderer.CARD_WIDTH);
                    if (cardIndex < cards.size()) {
                        Card card = cards.get(cardIndex);
                        cardListener.cardClicked(card);
                    }
                }
            }
        });
    }

    @Override
    public void cardClicked(Card card) {
        if (card.equals(selection)) {
            // Unselect
            selection = Card.BLANK;
        } else {
            selection = card;
        }
        repaint();
    }

    public Card getSelection() {
        return selection;
    }
}

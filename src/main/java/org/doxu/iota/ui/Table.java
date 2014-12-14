package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import org.doxu.iota.Board;
import org.doxu.iota.Card;

/**
 *
 * @author rafael
 */
public class Table extends JPanel {

    private static final Color TABLE_COLOR = new Color(153, 186, 132);

    private final Board board;

    public Table(Board board) {
        this.board = board;
        setBackground(TABLE_COLOR);
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int tableWidth = getWidth();
        int tableHeight = getHeight();

        Card[][] cards = board.getCards();
        int[] extents = board.getExtents();
        int cardsAcross = extents[2] - extents[0] + 1;
        int cardsDown = extents[3] - extents[1] + 1;
        int cardsWidth = cardsAcross * CardRenderer.CARD_WIDTH;
        int cardsHeight = cardsDown * CardRenderer.CARD_WIDTH;
        int xAnchor = (tableWidth - cardsWidth) / 2;
        int yAnchor = (tableHeight - cardsHeight) / 2;

        g2d.setColor(new Color(60, 60, 60));
        g2d.drawRect(xAnchor, yAnchor, cardsWidth, cardsHeight);

        System.out.println(tableWidth + "x" + tableHeight + " - " + cardsAcross + "x" + cardsDown + " - " + cardsWidth + "x" + cardsHeight);
        int padding = 0;
        for (int i = extents[0] - padding; i <= extents[2] + padding; i++) {
            for (int j = extents[1] - padding; j <= extents[3] + padding; j++) {
                if (!cards[i][j].isBlank()) {
                    CardRenderer.draw(g2d, cards[i][j], xAnchor + (i - extents[0] + padding) * CardRenderer.CARD_WIDTH, yAnchor + (j - extents[1] + padding) * CardRenderer.CARD_WIDTH);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}

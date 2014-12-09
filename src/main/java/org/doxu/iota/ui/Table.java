package org.doxu.iota.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.doxu.iota.Board;
import org.doxu.iota.Card;
import org.doxu.iota.Laydown;
import org.doxu.iota.PlayListener;
import org.doxu.iota.Player;

/**
 *
 * @author rafael
 */
public class Table extends JPanel implements PlayListener {

    private final Board board;

    public Table(Board board) {
        this.board = board;
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Card[][] cards = board.getCards();
        int[] extents = board.getExtents();
        int padding = 4;
        for (int i = extents[0] - padding; i <= extents[2] + padding; i++) {
            for (int j = extents[1] - padding; j <= extents[3] + padding; j++) {
                CardRenderer.draw(g2d, cards[i][j], (i - extents[0] + padding) * CardRenderer.CARD_WIDTH, (j - extents[1] + padding) * CardRenderer.CARD_WIDTH);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void turn(Player player, Laydown laydown) {
        repaint();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

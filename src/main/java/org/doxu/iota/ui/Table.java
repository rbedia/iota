package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import org.doxu.iota.Board;
import org.doxu.iota.BoardBounds;
import org.doxu.iota.Card;

public class Table extends JPanel implements ComponentListener {

    private static final Color TABLE_COLOR = new Color(153, 186, 132);
    private static final Color TABLE_BOUNDS_COLOR = new Color(90, 90, 90);
    private static final Color STARTING_CARD_COLOR = new Color(255, 0, 0);
    private static final Color GRID_COLOR = new Color(200, 200, 255);

    private final Board board;

    private int xAnchor;
    private int yAnchor;

    public Table(Board board) {
        this.board = board;
        setBackground(TABLE_COLOR);
        addComponentListener(this);
        xAnchor = 12;
        yAnchor = 12;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        drawGrid(g2d);
        drawTableBounds(g2d);

        int xTileOffset = Board.MIDDLE - xAnchor;
        int yTileOffset = Board.MIDDLE - yAnchor;

        Card[][] cards = board.getCards();
        for (int i = 0; i < getTableWidth(); i++) {
            for (int j = 0; j < getTableHeight(); j++) {
                Card card = cards[i + xTileOffset][j + yTileOffset];
                if (!card.isBlank()) {
                    int x = i * CardRenderer.CARD_WIDTH;
                    int y = j * CardRenderer.CARD_WIDTH;
                    CardRenderer.draw(g2d, card, x, y);
                    if (i == xAnchor && j == yAnchor) {
                        g2d.setColor(STARTING_CARD_COLOR);
                        g2d.drawRect(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
                    }
                }
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);
        Dimension tableDim = getSize();
        for (int i = CardRenderer.CARD_WIDTH; i < tableDim.height; i += CardRenderer.CARD_WIDTH) {
            g2d.drawLine(0, i, tableDim.width, i);
        }
        for (int i = CardRenderer.CARD_WIDTH; i < tableDim.width; i += CardRenderer.CARD_WIDTH) {
            g2d.drawLine(i, 0, i, tableDim.height);
        }
    }

    private void drawTableBounds(Graphics2D g2d) {
        Rectangle tableBounds = toScreenCoords(getTableBounds());
        g2d.setColor(TABLE_BOUNDS_COLOR);
        g2d.draw(tableBounds);
    }

    private Rectangle toScreenCoords(Rectangle tableCoords) {
        return new Rectangle(
                tableCoords.x * CardRenderer.CARD_WIDTH,
                tableCoords.y * CardRenderer.CARD_WIDTH,
                tableCoords.width * CardRenderer.CARD_WIDTH,
                tableCoords.height * CardRenderer.CARD_WIDTH);
    }

    private int getTableWidth() {
        return (int) Math.floor(getWidth() / CardRenderer.CARD_WIDTH);
    }

    private int getTableHeight() {
        return (int) Math.floor(getHeight() / CardRenderer.CARD_WIDTH);
    }

    private Rectangle getTableBounds() {
        BoardBounds bounds = board.getBounds();
        return new Rectangle(
                toTableCoordX(bounds.getMinX()),
                toTableCoordY(bounds.getMinY()),
                bounds.getMaxX() - bounds.getMinX() + 1,
                bounds.getMaxY() - bounds.getMinY() + 1);
    }

    /**
     * Converts a board coordinate to a table coordinate.
     *
     * @param x
     * @return
     */
    private int toTableCoordX(int x) {
        int xTileOffset = Board.MIDDLE - xAnchor;
        return x - xTileOffset;
    }

    private int toTableCoordY(int y) {
        int yTileOffset = Board.MIDDLE - yAnchor;
        return y - yTileOffset;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        xAnchor = getTableWidth() / 2;
        yAnchor = getTableHeight() / 2;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}

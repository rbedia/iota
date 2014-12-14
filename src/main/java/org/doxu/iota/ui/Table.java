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

/**
 *
 * @author rafael
 */
public class Table extends JPanel implements ComponentListener {

    private static final Color TABLE_COLOR = new Color(153, 186, 132);
    private static final Color TABLE_BOUNDS_COLOR = new Color(90, 90, 90);

    private final Board board;

    private int xAnchor;
    private int yAnchor;

    public Table(Board board) {
        this.board = board;
        setBackground(TABLE_COLOR);
        addComponentListener(this);
//        xAnchor = -10000;
//        yAnchor = -10000;
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

        g2d.setColor(new Color(200, 200, 255));
        Dimension tableDim = getSize();
        for (int i = CardRenderer.CARD_WIDTH; i < tableDim.height; i += CardRenderer.CARD_WIDTH) {
            g2d.drawLine(0, i, tableDim.width, i);
        }
        for (int i = CardRenderer.CARD_WIDTH; i < tableDim.width; i += CardRenderer.CARD_WIDTH) {
            g2d.drawLine(i, 0, i, tableDim.height);
        }

        Card[][] cards = board.getCards();

        BoardBounds bounds = board.getBounds();
        Rectangle tableBounds = getTableBounds();
        int xAnchor = tableBounds.x;
        int yAnchor = tableBounds.y;

        g2d.setColor(TABLE_BOUNDS_COLOR);
        g2d.draw(tableBounds);

        for (int i = bounds.getMinX(); i <= bounds.getMaxX(); i++) {
            for (int j = bounds.getMinY(); j <= bounds.getMaxY(); j++) {
                if (!cards[i][j].isBlank()) {
                    int x = xAnchor + (i - bounds.getMinX()) * CardRenderer.CARD_WIDTH;
                    int y = yAnchor + (j - bounds.getMinY()) * CardRenderer.CARD_WIDTH;
                    CardRenderer.draw(g2d, cards[i][j], x, y);
                    if (i == Board.MIDDLE && j == Board.MIDDLE) {
                        g2d.setColor(new Color(255, 0, 0));
                        g2d.drawRect(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
                    }
                }
            }
        }
    }

    private int getTableWidth() {
        return (int) Math.floor(getWidth() / CardRenderer.CARD_WIDTH);
    }

    private int getTableHeight() {
        return (int) Math.floor(getHeight() / CardRenderer.CARD_WIDTH);
    }

    private Rectangle getTableBounds() {
        int tableWidth = getTableWidth() * CardRenderer.CARD_WIDTH;
        int tableHeight = getTableHeight() * CardRenderer.CARD_WIDTH;

        int xTileOffset = Board.MIDDLE - xAnchor;
        int yTileOffset = Board.MIDDLE - yAnchor;

        Dimension boardDim = getBoardDim();
        int xAnchor = (tableWidth - boardDim.width) / 2;
        int yAnchor = (tableHeight - boardDim.height) / 2;
        xAnchor -= (xAnchor % CardRenderer.CARD_WIDTH);
        yAnchor -= (yAnchor % CardRenderer.CARD_WIDTH);

//        System.out.println(tableWidth + "x" + tableHeight + " - " + cardsAcross + "x" + cardsDown + " - " + cardsWidth + "x" + cardsHeight);
        return new Rectangle(xAnchor, yAnchor, boardDim.width, boardDim.height);
    }

    private Dimension getBoardDim() {
        BoardBounds bounds = board.getBounds();
        int cardsAcross = bounds.getMaxX() - bounds.getMinX() + 1;
        int cardsDown = bounds.getMaxY() - bounds.getMinY() + 1;
        int cardsWidth = cardsAcross * CardRenderer.CARD_WIDTH;
        int cardsHeight = cardsDown * CardRenderer.CARD_WIDTH;
        Dimension boardDim = new Dimension(cardsWidth, cardsHeight);
        return boardDim;
    }

    @Override
    public void componentResized(ComponentEvent e) {
//        Rectangle tableBounds = getBounds();
//        Dimension boardDim = getBoardDim();
//        Rectangle boardRect = new Rectangle(new Point(xAnchor, yAnchor), boardDim);
//        if (!tableBounds.contains(boardRect)) {
//            xAnchor = getWidth() / 2;
//            yAnchor = getHeight() / 2;
//        }
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

package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.doxu.iota.board.Board;
import org.doxu.iota.board.BoardBounds;
import org.doxu.iota.Card;
import org.doxu.iota.Game;
import org.doxu.iota.Location;

public class Table extends JPanel implements ComponentListener {

    private static final Color TABLE_COLOR = new Color(153, 186, 132);
    private static final Color TABLE_BOUNDS_COLOR = new Color(90, 90, 90);
    private static final Color STARTING_CARD_COLOR = new Color(255, 0, 0);
    private static final Color SELECTED_TURN_CARD_COLOR = new Color(152, 51, 153);
    private static final Color NEXT_TURN_CARD_COLOR = new Color(0, 0, 0);
    private static final Color GRID_COLOR = new Color(200, 200, 200);

    private final Board gameBoard;
    private Board board;

    private int xAnchor;
    private int yAnchor;

    private final List<Location> selection;

    private final List<Location> nextTurn;

    public Table(Game game) {
        gameBoard = game.getBoard();
        board = gameBoard;
        setBackground(TABLE_COLOR);
        addComponentListener(this);
        xAnchor = 12;
        yAnchor = 12;
        selection = new ArrayList<>();
        nextTurn = new ArrayList<>();
        addClickListener(new LocationListener() {

            @Override
            public void locationClicked(Location location) {
                System.out.println("X - " + location.getX() + " Y - " + location.getY());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        drawGrid(g);
        drawTableBounds((Graphics2D) g);

        int xTileOffset = Board.MIDDLE - xAnchor;
        int yTileOffset = Board.MIDDLE - yAnchor;

        for (int i = 0; i < getTableWidth(); i++) {
            for (int j = 0; j < getTableHeight(); j++) {
                Card card = board.getCard(i + xTileOffset, j + yTileOffset);
                if (!card.isBlank()) {
                    int x = i * CardRenderer.CARD_WIDTH;
                    int y = j * CardRenderer.CARD_WIDTH;
                    Graphics g2card = g.create(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
                    CardRenderer.draw(g2card, card);
                    g2card.dispose();
                    if (i == xAnchor && j == yAnchor) {
                        g.setColor(STARTING_CARD_COLOR);
                        g.drawRect(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
                    }
                }
            }
        }

        for (Location location : selection) {
            int x = (location.getX() - xTileOffset) * CardRenderer.CARD_WIDTH;
            int y = (location.getY() - yTileOffset) * CardRenderer.CARD_WIDTH;
            g.setColor(SELECTED_TURN_CARD_COLOR);
            g.drawRect(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
            g.drawRect(x + 1, y + 1, CardRenderer.CARD_WIDTH - 2, CardRenderer.CARD_WIDTH - 2);
        }

        for (Location location : nextTurn) {
            int x = (location.getX() - xTileOffset) * CardRenderer.CARD_WIDTH;
            int y = (location.getY() - yTileOffset) * CardRenderer.CARD_WIDTH;
            g.setColor(NEXT_TURN_CARD_COLOR);
            g.drawRect(x, y, CardRenderer.CARD_WIDTH, CardRenderer.CARD_WIDTH);
            g.drawRect(x + 1, y + 1, CardRenderer.CARD_WIDTH - 2, CardRenderer.CARD_WIDTH - 2);
        }
    }

    private void drawGrid(Graphics g2d) {
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

    private int toBoardCoordX(int x) {
        int xTileOffset = Board.MIDDLE - xAnchor;
        return x + xTileOffset;
    }

    private int toBoardCoordY(int y) {
        int yTileOffset = Board.MIDDLE - yAnchor;
        return y + yTileOffset;
    }

    public void setSelection(List<Location> selection) {
        this.selection.clear();
        this.selection.addAll(selection);
        repaint();
    }

    public void setNextTurn(List<Location> nextTurn) {
        this.nextTurn.clear();
        this.nextTurn.addAll(nextTurn);
        repaint();
    }

    public void addClickListener(final LocationListener locationListener) {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Point loc = e.getPoint();
                int xIndex = (int) Math.floor((double) loc.x / CardRenderer.CARD_WIDTH);
                int yIndex = (int) Math.floor((double) loc.y / CardRenderer.CARD_WIDTH);
                int xBoard = toBoardCoordX(xIndex);
                int yBoard = toBoardCoordY(yIndex);
                locationListener.locationClicked(new Location(xBoard, yBoard));
            }
        });
    }

    public void resetBoard() {
        board = gameBoard;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

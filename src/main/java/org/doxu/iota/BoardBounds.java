package org.doxu.iota;

/**
 * Contains the bounds of the board that are being used by cards.
 *
 * @author rafael
 */
public class BoardBounds {

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public void init() {
        minX = Board.MIDDLE;
        minY = Board.MIDDLE;
        maxX = Board.MIDDLE;
        maxY = Board.MIDDLE;
    }

    public BoardBounds copy() {
        BoardBounds bounds = new BoardBounds();
        bounds.minX = minX;
        bounds.minY = minY;
        bounds.maxX = maxX;
        bounds.maxY = maxY;
        return bounds;
    }

    public void updateMinMax(int x, int y) {
        if (x < minX) {
            minX = x;
        }
        if (x > maxX) {
            maxX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (y > maxY) {
            maxY = y;
        }
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}

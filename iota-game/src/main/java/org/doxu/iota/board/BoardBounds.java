package org.doxu.iota.board;

/**
 * Contains the bounds of the board that are being used by cards.
 */
public class BoardBounds {

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public BoardBounds() {
        init();
    }

    public BoardBounds(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public final void init() {
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.minX;
        hash = 41 * hash + this.minY;
        hash = 41 * hash + this.maxX;
        hash = 41 * hash + this.maxY;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoardBounds other = (BoardBounds) obj;
        if (this.minX != other.minX) {
            return false;
        }
        if (this.minY != other.minY) {
            return false;
        }
        if (this.maxX != other.maxX) {
            return false;
        }
        if (this.maxY != other.maxY) {
            return false;
        }
        return true;
    }
}

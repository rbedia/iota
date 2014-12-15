package org.doxu.iota;

public class Location {

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location moveRight() {
        return new Location(x + 1, y);
    }

    public Location moveLeft() {
        return new Location(x - 1, y);
    }

    public Location moveDown() {
        return new Location(x, y + 1);
    }

    public Location moveUp() {
        return new Location(x, y - 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

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

    public static Location getLeftmost(Location... locations) {
        Location loc = locations[0];
        for (int i = 1; i < locations.length; i++) {
            if (locations[i].getX() < loc.getX()) {
                loc = locations[i];
            }
        }
        return loc;
    }

    public static Location getRightmost(Location... locations) {
        Location loc = locations[0];
        for (int i = 1; i < locations.length; i++) {
            if (locations[i].getX() > loc.getX()) {
                loc = locations[i];
            }
        }
        return loc;
    }

    public static Location getTopmost(Location... locations) {
        Location loc = locations[0];
        for (int i = 1; i < locations.length; i++) {
            if (locations[i].getY() < loc.getY()) {
                loc = locations[i];
            }
        }
        return loc;
    }

    public static Location getBottommost(Location... locations) {
        Location loc = locations[0];
        for (int i = 1; i < locations.length; i++) {
            if (locations[i].getY() > loc.getY()) {
                loc = locations[i];
            }
        }
        return loc;
    }
}

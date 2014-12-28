package org.doxu.iota;

public class Location {

    public enum Direction {
        RIGHT, LEFT, DOWN, UP
    };

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location right() {
        return new Location(x + 1, y);
    }

    public Location left() {
        return new Location(x - 1, y);
    }

    public Location down() {
        return new Location(x, y + 1);
    }

    public Location up() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
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
        final Location other = (Location) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}

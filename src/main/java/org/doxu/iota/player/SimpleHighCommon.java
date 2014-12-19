package org.doxu.iota.player;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.Board;
import org.doxu.iota.Location;

public class SimpleHighCommon {

    public static List<Location> collectValidLocations(Board board) {
        List<Location> locations = new ArrayList<>();
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                Location location = new Location(x, y);
                if (!board.isOverlappingCard(location) && board.isTouchingBoard(location)) {
                    locations.add(location);
                }
            }
        }
        return locations;
    }
}

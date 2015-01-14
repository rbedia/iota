package org.doxu.iota.player;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.board.Board;
import org.doxu.iota.Card;
import org.doxu.iota.Deck;
import org.doxu.iota.Hand;
import org.doxu.iota.Location;
import org.doxu.iota.Player;
import org.doxu.iota.turn.PassTurn;
import org.doxu.iota.turn.TradeTurn;
import org.doxu.iota.turn.Turn;

public class SimpleHighCommon {

    public static Turn basicTrade(Deck deck, Hand hand, Player player) {
        int deckCount = deck.count();
        int handCount = hand.getCards().size();
        int count = Math.min(deckCount, handCount);
        List<Card> tradeCards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tradeCards.add(hand.getCards().get(i));
        }
        if (tradeCards.size() > 0) {
            return new TradeTurn(tradeCards, player);
        } else {
            return new PassTurn(player);
        }
    }

    public static List<Location> collectValidLocations(Board board) {
        List<Location> locations = new ArrayList<>();
        int minX = Math.max(0, board.getBounds().getMinX() - 1);
        int minY = Math.max(0, board.getBounds().getMinY() - 1);
        int maxX = Math.min(Board.BOARD_SIZE - 1, board.getBounds().getMaxX() + 1);
        int maxY = Math.min(Board.BOARD_SIZE - 1, board.getBounds().getMaxY() + 1);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Location location = new Location(x, y);
                if (!board.isOverlappingCard(location) && board.isTouchingBoard(location)) {
                    locations.add(location);
                }
            }
        }
        return locations;
    }

    public static List<Location> collectValidLocations(Board board, Location startingLocation) {
        List<Location> locations = new ArrayList<>();
        // Search left
        Location location = startingLocation.left();
        while (board.isOverlappingCard(location)) {
            location = location.left();
        }
        locations.add(location);
        // Search right
        location = startingLocation.right();
        while (board.isOverlappingCard(location)) {
            location = location.right();
        }
        locations.add(location);
        // Search up
        location = startingLocation.up();
        while (board.isOverlappingCard(location)) {
            location = location.up();
        }
        locations.add(location);
        // Search down
        location = startingLocation.down();
        while (board.isOverlappingCard(location)) {
            location = location.down();
        }
        locations.add(location);

        return locations;
    }

    public static List<Location> collectValidLocations(Board board, Location location1, Location location2) {
        List<Location> locations = new ArrayList<>();
        boolean horizontal = location1.getY() == location2.getY();
        if (horizontal) {
            // Search left
            Location leftLocation = location1.getX() < location2.getX() ? location1 : location2;
            Location location = leftLocation.left();
            while (board.isOverlappingCard(location)) {
                location = location.left();
            }
            locations.add(location);
            // Search right
            Location rightLocation = location1.getX() > location2.getX() ? location1 : location2;
            location = rightLocation.right();
            while (board.isOverlappingCard(location)) {
                location = location.right();
            }
            locations.add(location);
        } else {
            // Search up
            Location topLocation = location1.getY() < location2.getY() ? location1 : location2;
            Location location = topLocation.up();
            while (board.isOverlappingCard(location)) {
                location = location.up();
            }
            locations.add(location);
            // Search down
            Location bottomLocation = location1.getY() > location2.getY() ? location1 : location2;
            location = bottomLocation.down();
            while (board.isOverlappingCard(location)) {
                location = location.down();
            }
            locations.add(location);
        }
        return locations;
    }

    public static List<Location> collectValidLocations(Board board, Location location1, Location location2, Location location3) {
        List<Location> locations = new ArrayList<>();
        boolean horizontal = location1.getY() == location2.getY();
        if (horizontal) {
            // Search left
            Location leftLocation = Location.getLeftmost(location1, location2, location3);
            Location location = leftLocation.left();
            while (board.isOverlappingCard(location)) {
                location = location.left();
            }
            locations.add(location);
            // Search right
            Location rightLocation = Location.getRightmost(location1, location2, location3);
            location = rightLocation.right();
            while (board.isOverlappingCard(location)) {
                location = location.right();
            }
            locations.add(location);
        } else {
            // Search up
            Location topLocation = Location.getTopmost(location1, location2, location3);
            Location location = topLocation.up();
            while (board.isOverlappingCard(location)) {
                location = location.up();
            }
            locations.add(location);
            // Search down
            Location bottomLocation = Location.getBottommost(location1, location2, location3);
            location = bottomLocation.down();
            while (board.isOverlappingCard(location)) {
                location = location.down();
            }
            locations.add(location);
        }
        return locations;
    }
}

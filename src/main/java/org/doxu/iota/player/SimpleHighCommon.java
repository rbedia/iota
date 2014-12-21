package org.doxu.iota.player;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.Board;
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

    public static List<Location> collectValidLocations(Board board, Location startingLocation) {
        List<Location> locations = new ArrayList<>();
        // Search left
        Location location = startingLocation.moveLeft();
        while (board.isOverlappingCard(location)) {
            location = location.moveLeft();
        }
        locations.add(location);
        // Search right
        location = startingLocation.moveRight();
        while (board.isOverlappingCard(location)) {
            location = location.moveRight();
        }
        locations.add(location);
        // Search up
        location = startingLocation.moveUp();
        while (board.isOverlappingCard(location)) {
            location = location.moveUp();
        }
        locations.add(location);
        // Search down
        location = startingLocation.moveDown();
        while (board.isOverlappingCard(location)) {
            location = location.moveDown();
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
            Location location = leftLocation.moveLeft();
            while (board.isOverlappingCard(location)) {
                location = location.moveLeft();
            }
            locations.add(location);
            // Search right
            Location rightLocation = location1.getX() > location2.getX() ? location1 : location2;
            location = rightLocation.moveRight();
            while (board.isOverlappingCard(location)) {
                location = location.moveRight();
            }
            locations.add(location);
        } else {
            // Search up
            Location topLocation = location1.getY() < location2.getY() ? location1 : location2;
            Location location = topLocation.moveUp();
            while (board.isOverlappingCard(location)) {
                location = location.moveUp();
            }
            locations.add(location);
            // Search down
            Location bottomLocation = location1.getY() > location2.getY() ? location1 : location2;
            location = bottomLocation.moveDown();
            while (board.isOverlappingCard(location)) {
                location = location.moveDown();
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
            Location location = leftLocation.moveLeft();
            while (board.isOverlappingCard(location)) {
                location = location.moveLeft();
            }
            locations.add(location);
            // Search right
            Location rightLocation = Location.getRightmost(location1, location2, location3);
            location = rightLocation.moveRight();
            while (board.isOverlappingCard(location)) {
                location = location.moveRight();
            }
            locations.add(location);
        } else {
            // Search up
            Location topLocation = Location.getTopmost(location1, location2, location3);
            Location location = topLocation.moveUp();
            while (board.isOverlappingCard(location)) {
                location = location.moveUp();
            }
            locations.add(location);
            // Search down
            Location bottomLocation = Location.getBottommost(location1, location2, location3);
            location = bottomLocation.moveDown();
            while (board.isOverlappingCard(location)) {
                location = location.moveDown();
            }
            locations.add(location);
        }
        return locations;
    }
}

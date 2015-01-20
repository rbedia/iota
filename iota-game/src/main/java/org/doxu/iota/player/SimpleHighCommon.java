package org.doxu.iota.player;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.board.Board;
import org.doxu.iota.Card;
import org.doxu.iota.Deck;
import org.doxu.iota.Hand;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
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

    public static List<Location> collectValidLocations(Board board, List<Location> used) {
        if (used.isEmpty()) {
            throw new IllegalArgumentException("Must specify at least one Location");
        }
        if (used.size() == 1) {
            return collectValidLocationsSingle(board, used.get(0));
        }
        List<Location> locations = new ArrayList<>();
        boolean horizontal = used.get(0).getY() == used.get(1).getY();
        if (horizontal) {
            // Search left
            Location leftLocation = Location.getLeftmost(used);
            locations.add(searchLeft(leftLocation, board));
            // Search right
            Location rightLocation = Location.getRightmost(used);
            locations.add(searchRight(rightLocation, board));
        } else {
            // Search up
            Location topLocation = Location.getTopmost(used);
            locations.add(searchUp(topLocation, board));
            // Search down
            Location bottomLocation = Location.getBottommost(used);
            locations.add(searchDown(bottomLocation, board));
        }
        return locations;
    }

    public static List<ScoreLaydown> findOptions(Hand hand, Board board) {
        List<ScoreLaydown> options = new ArrayList<>();
        for (Card card : hand.getCards()) {
            List<Location> locations = SimpleHighCommon.collectValidLocations(board);
            while (!locations.isEmpty()) {
                Location location = locations.remove(0);
                Laydown laydown = new Laydown();
                laydown.addMove(new Move(location, card));
                try {
                    Board boardCopy = board.overlay();
                    int score = boardCopy.applyLaydown(laydown);
                    options.add(new ScoreLaydown(score, laydown));
                } catch (IllegalLaydownException ex) {
                }
            }
        }
        return options;
    }

    public static List<ScoreLaydown> findOptions(Hand hand, Board board, List<ScoreLaydown> input, boolean doubleScore) {
        List<ScoreLaydown> options = new ArrayList<>();
        for (ScoreLaydown scoreLaydown : input) {
            for (Card card : hand.getCards()) {
                if (!scoreLaydown.laydown.contains(card)) {
                    List<Location> laydownLocations = scoreLaydown.laydown.getLocations();
                    List<Location> locations = SimpleHighCommon.collectValidLocations(board, laydownLocations);
                    for (Location location : locations) {
                        Laydown laydown = scoreLaydown.laydown.copy();
                        laydown.addMove(new Move(location, card));
                        try {
                            Board boardCopy = board.overlay();
                            int score = boardCopy.applyLaydown(laydown);
                            if (doubleScore) {
                                score *= 2;
                            }
                            options.add(new ScoreLaydown(score, laydown));
                        } catch (IllegalLaydownException ex) {
                        }
                    }
                }
            }
        }
        return options;
    }

    private static List<Location> collectValidLocationsSingle(Board board, Location startingLocation) {
        List<Location> locations = new ArrayList<>();
        locations.add(searchLeft(startingLocation, board));
        locations.add(searchRight(startingLocation, board));
        locations.add(searchUp(startingLocation, board));
        locations.add(searchDown(startingLocation, board));
        return locations;
    }

    private static Location searchDown(Location startingLocation, Board board) {
        Location location = startingLocation.down();
        while (board.isOverlappingCard(location)) {
            location = location.down();
        }
        return location;
    }

    private static Location searchUp(Location startingLocation, Board board) {
        Location location = startingLocation.up();
        while (board.isOverlappingCard(location)) {
            location = location.up();
        }
        return location;
    }

    private static Location searchRight(Location startingLocation, Board board) {
        Location location = startingLocation.right();
        while (board.isOverlappingCard(location)) {
            location = location.right();
        }
        return location;
    }

    private static Location searchLeft(Location startingLocation, Board board) {
        Location location = startingLocation.left();
        while (board.isOverlappingCard(location)) {
            location = location.left();
        }
        return location;
    }
}

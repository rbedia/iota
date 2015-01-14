package org.doxu.iota.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.attr.LotValidator;

public class Board {

    public static final int BOARD_SIZE = 127;

    public static final int MIDDLE = (BOARD_SIZE + 1) / 2;

    private final BoardView board;

    public Board() {
        this(new BoardBase());
    }

    public Board(BoardView board) {
        this.board = board;
    }

    public final void init() {
        board.init();
    }

    public Board overlay() {
        return new Board(new BoardOverlay(board));
    }

    public Board overlay(List<Move> moves) {
        return new Board(new BoardOverlay(board, moves));
    }

    public Card getCard(Location location) {
        return board.getCard(location);
    }

    public Card getCard(int x, int y) {
        return board.getCard(new Location(x, y));
    }

    public BoardBounds getBounds() {
        return board.getBounds();
    }

    public Location findCard(Card card) {
        return board.findCard(card);
    }

    public void load(String[][] cards) throws IllegalLaydownException {
        int xOffset = (BOARD_SIZE - cards[0].length) / 2;
        int yOffset = (BOARD_SIZE - cards.length) / 2;
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
                board.applyCard(new Location(xOffset + j, yOffset + i), Card.create(cards[i][j]));
            }
        }
        validateBoard();
    }

    public int applyLaydown(Laydown laydown) throws IllegalLaydownException {
        validLaydown(laydown);
        List<Move> moves = laydown.getMoves();
        for (Move move : moves) {
            board.applyCard(move.getLocation(), move.getCard());
        }
        return calculateScore(moves);
    }

    private int calculateScore(List<Move> moves) {
        int sum = 0;
        int lots = 0;
        Set<Card> hCounted = new HashSet<>();
        Set<Card> vCounted = new HashSet<>();
        for (Move move : moves) {
            int rowLength = 0;
            // search right
            for (Card card : CardIterable.right(board, move.getLocation().right())) {
                rowLength++;
                sum += tallyCard(hCounted, card);
            }
            // search left
            for (Card card : CardIterable.left(board, move.getLocation().left())) {
                rowLength++;
                sum += tallyCard(hCounted, card);
            }
            if (rowLength > 0 && !hCounted.contains(move.getCard())) {
                sum += move.getCard().getPoints();
                hCounted.add(move.getCard());
                rowLength++;
            }
            if (rowLength == 4) {
                lots++;
            }
            rowLength = 0;
            // search up
            for (Card card : CardIterable.up(board, move.getLocation().up())) {
                rowLength++;
                sum += tallyCard(vCounted, card);
            }
            // search down
            for (Card card : CardIterable.down(board, move.getLocation().down())) {
                rowLength++;
                sum += tallyCard(vCounted, card);
            }
            if (rowLength > 0 && !vCounted.contains(move.getCard())) {
                sum += move.getCard().getPoints();
                vCounted.add(move.getCard());
                rowLength++;
            }
            if (rowLength == 4) {
                lots++;
            }
        }
        return sum * (1 << lots);
    }

    private int tallyCard(Set<Card> cards, Card card) {
        int points = 0;
        if (!cards.contains(card)) {
            points = card.getPoints();
            cards.add(card);
        }
        return points;
    }

    public void validLaydown(Laydown laydown) throws IllegalLaydownException {
        List<Move> moves = laydown.getMoves();
        boolean allowed = false;
        for (Move move : moves) {
            if (isTouchingBoard(move.getLocation())) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            throw new IllegalLaydownException("Laydown must touch existing cards.");
        }
        allowed = true;
        for (Move move : moves) {
            if (isOverlappingCard(move.getLocation())) {
                allowed = false;
                break;
            }
        }
        if (!allowed) {
            throw new IllegalLaydownException("Laydown overlaps existing cards.");
        }
        Board boardCopy = overlay(moves);
        boardCopy.validateBoard(laydown.getLocations());
    }

    public boolean isTouchingBoard(Location location) {
        int x = location.getX() - 1;
        int y = location.getY();
        if (isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank()) {
            return true;
        }
        x = location.getX() + 1;
        y = location.getY();
        if (isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank()) {
            return true;
        }
        x = location.getX();
        y = location.getY() - 1;
        if (isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank()) {
            return true;
        }
        x = location.getX();
        y = location.getY() + 1;
        return isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank();
    }

    public boolean isOverlappingCard(Location location) {
        if (!isInRange(location.getX()) || !isInRange(location.getY())) {
            throw new IllegalArgumentException("Location is not on the board.");
        }
        return !board.getCard(location).isBlank();
    }

    private boolean isInRange(int x) {
        return x >= 0 && x < BOARD_SIZE;
    }

    /**
     * Validates the entire board.
     *
     * @throws IllegalLaydownException
     */
    private void validateBoard() throws IllegalLaydownException {
        // Validate rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<Card> lot = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                Card card = getCard(i, j);
                if (!card.isBlank()) {
                    lot.add(card);
                } else {
                    LotValidator.validate(lot);
                    lot.clear();
                }
            }
        }
        // Validate columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<Card> lot = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                Card card = getCard(j, i);
                if (!card.isBlank()) {
                    lot.add(card);
                } else {
                    LotValidator.validate(lot);
                    lot.clear();
                }
            }
        }
    }

    /**
     * Validates the cards in the rows and columns specified by locations.
     *
     * @param locations
     * @throws IllegalLaydownException
     */
    private void validateBoard(List<Location> locations) throws IllegalLaydownException {
        // Validate rows
        for (Location location : locations) {
            List<Card> cards = gatherRow(location);
            LotValidator.validate(cards);
        }
        // Validate columns
        for (Location location : locations) {
            List<Card> cards = gatherColumn(location);
            LotValidator.validate(cards);
        }
    }

    private List<Card> gatherRow(Location start) {
        List<Card> cardRow = new ArrayList<>();
        // search right
        Location location = start;
        Card card = getCard(location);
        while (!card.isBlank()) {
            cardRow.add(card);
            location = location.right();
            card = getCard(location);
        }
        // search left
        location = start.left();
        card = getCard(location);
        while (!card.isBlank()) {
            cardRow.add(card);
            location = location.left();
            card = getCard(location);
        }
        return cardRow;
    }

    private List<Card> gatherColumn(Location start) {
        List<Card> cardColumn = new ArrayList<>();
        // search up
        Location location = start;
        Card card = getCard(location);
        while (!card.isBlank()) {
            cardColumn.add(card);
            location = location.up();
            card = getCard(location);
        }
        // search down
        location = start.down();
        card = getCard(location);
        while (!card.isBlank()) {
            cardColumn.add(card);
            location = location.down();
            card = getCard(location);
        }
        return cardColumn;
    }

    public void print() {
        for (int y = board.getBounds().getMinY(); y < board.getBounds().getMaxY() + 1; y++) {
            for (int x = board.getBounds().getMinX(); x < board.getBounds().getMaxX() + 1; x++) {
                System.out.print(" " + board.getCard(new Location(x, y)));
            }
            System.out.println();
        }
    }

    public void addFirst(Card first) {
        Location location = new Location(MIDDLE, MIDDLE);
        board.applyCard(location, first);
    }
}

package org.doxu.iota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.doxu.iota.attr.LotValidator;

/**
 *
 * @author rafael
 */
public class Board {

    public static final int BOARD_SIZE = 127;

    public static final int MIDDLE = (BOARD_SIZE + 1) / 2;

    private final Card[][] cards;

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public Board copy() {
        Board board = new Board(copyCards());
        board.minX = minX;
        board.minY = minY;
        board.maxX = maxX;
        board.maxY = maxY;
        return board;
    }

    private Board(Card[][] cards) {
        this.cards = cards;
    }

    public Board() {
        minX = BOARD_SIZE;
        minY = BOARD_SIZE;
        maxX = 0;
        maxY = 0;
        cards = new Card[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                cards[x][y] = Card.BLANK;
            }
        }
    }

    public int applyLaydown(Laydown laydown) throws IllegalLaydownException {
        validLaydown(laydown);
        List<Move> moves = laydown.getMoves();
        for (Move move : moves) {
            applyCard(move.getLocation(), move.getCard());
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
            Location location = move.getLocation().moveRight();
            Card card = getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!hCounted.contains(card)) {
                    sum += card.getPoints();
                    hCounted.add(card);
                }
                location = location.moveRight();
                card = getCard(location);
            }
            // search left
            location = move.getLocation().moveLeft();
            card = getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!hCounted.contains(card)) {
                    sum += card.getPoints();
                    hCounted.add(card);
                }
                location = location.moveLeft();
                card = getCard(location);
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
            location = move.getLocation().moveUp();
            card = getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!vCounted.contains(card)) {
                    sum += card.getPoints();
                    vCounted.add(card);
                }
                location = location.moveUp();
                card = getCard(location);
            }
            // search down
            location = move.getLocation().moveDown();
            card = getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!vCounted.contains(card)) {
                    sum += card.getPoints();
                    vCounted.add(card);
                }
                location = location.moveDown();
                card = getCard(location);
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
        Card[][] cardCopy = copyCards();
        for (Move move : moves) {
            cardCopy[move.getLocation().getX()][move.getLocation().getY()] = move.getCard();
        }
        validateBoard(cardCopy);
    }

    private Card[][] copyCards() {
        Card[][] cardsCopy = new Card[cards.length][cards[0].length];
        for (int i = 0; i < cardsCopy.length; i++) {
            cardsCopy[i] = Arrays.copyOf(cards[i], cards[i].length);
        }
        return cardsCopy;
    }

    public boolean isTouchingBoard(Location location) {
        int x = location.getX() - 1;
        int y = location.getY();
        if (isInRange(x) && isInRange(y) && cards[x][y] != Card.BLANK) {
            return true;
        }
        x = location.getX() + 1;
        y = location.getY();
        if (isInRange(x) && isInRange(y) && cards[x][y] != Card.BLANK) {
            return true;
        }
        x = location.getX();
        y = location.getY() - 1;
        if (isInRange(x) && isInRange(y) && cards[x][y] != Card.BLANK) {
            return true;
        }
        x = location.getX();
        y = location.getY() + 1;
        return isInRange(x) && isInRange(y) && cards[x][y] != Card.BLANK;
    }

    public boolean isOverlappingCard(Location location) {
        if (!isInRange(location.getX()) || !isInRange(location.getY())) {
            throw new IllegalArgumentException("Location is not on the board.");
        }
        return cards[location.getX()][location.getY()] != Card.BLANK;
    }

    private boolean isInRange(int x) {
        return x >= 0 && x < BOARD_SIZE;
    }

    private void validateBoard(Card[][] cardCopy) throws IllegalLaydownException {
        // Validate rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<Card> lot = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (cardCopy[i][j] != Card.BLANK) {
                    lot.add(cardCopy[i][j]);
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
                if (cardCopy[j][i] != Card.BLANK) {
                    lot.add(cardCopy[j][i]);
                } else {
                    LotValidator.validate(lot);
                    lot.clear();
                }
            }
        }
    }

    private void applyCard(Location location, Card card) {
        cards[location.getX()][location.getY()] = card;
        updateMinMax(location.getX(), location.getY());
    }

    private void updateMinMax(int x, int y) {
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

    public void print() {
        for (int x = minX; x < maxX + 1; x++) {
            for (int y = minY; y < maxY + 1; y++) {
                System.out.print(" " + cards[x][y]);
            }
            System.out.println();
        }
    }

    public void addFirst(Card first) {
        Location location = new Location(MIDDLE, MIDDLE);
        applyCard(location, first);
    }

    private Card getCard(Location location) {
        return cards[location.getX()][location.getY()];
    }
}

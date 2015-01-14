package org.doxu.iota.board;

import java.util.Iterator;
import org.doxu.iota.Card;
import org.doxu.iota.Location;
import org.doxu.iota.Location.Direction;

public class CardIterable implements Iterable<Card> {

    private final BoardView board;

    private final Direction direction;

    private final Location start;

    public CardIterable(BoardView board, Location start, Direction direction) {
        this.board = board;
        this.start = start;
        this.direction = direction;
    }

    @Override
    public Iterator<Card> iterator() {
        return new Iterator<Card>() {

            Location position = start;
            Card card = board.getCard(start);

            @Override
            public boolean hasNext() {
                return !card.isBlank();
            }

            @Override
            public Card next() {
                Card tempCard = card;
                position = step();
                card = board.getCard(position);
                return tempCard;
            }

            private Location step() throws IllegalArgumentException {
                switch (direction) {
                    case RIGHT:
                        return position.right();
                    case LEFT:
                        return position.left();
                    case DOWN:
                        return position.down();
                    case UP:
                        return position.up();
                    default:
                        throw new IllegalArgumentException("Unsupported direction: " + direction);
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not allowed.");
            }
        };
    }

    public static Iterable<Card> right(BoardView board, Location start) {
        return new CardIterable(board, start, Location.Direction.RIGHT);
    }

    public static Iterable<Card> left(BoardView board, Location start) {
        return new CardIterable(board, start, Location.Direction.LEFT);
    }

    public static Iterable<Card> down(BoardView board, Location start) {
        return new CardIterable(board, start, Location.Direction.DOWN);
    }

    public static Iterable<Card> up(BoardView board, Location start) {
        return new CardIterable(board, start, Location.Direction.UP);
    }
}

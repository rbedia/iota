package org.doxu.iota;

public class BoardBase implements BoardView {

    public static final int BOARD_SIZE = 127;

    private final Card[][] cards;

    private final BoardBounds bounds;

    public BoardBase() {
        bounds = new BoardBounds();
        cards = new Card[BOARD_SIZE][BOARD_SIZE];
        init();
    }

    @Override
    public final void init() {
        bounds.init();

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                cards[x][y] = Card.BLANK;
            }
        }
    }

    @Override
    public Location findCard(Card card) {
        for (int y = bounds.getMinY(); y < bounds.getMaxY() + 1; y++) {
            for (int x = bounds.getMinX(); x < bounds.getMaxX() + 1; x++) {
                if (cards[x][y].equals(card)) {
                    return new Location(x, y);
                }
            }
        }
        return null;
    }

    @Override
    public Card getCard(Location location) {
        return cards[location.getX()][location.getY()];
    }

    @Override
    public Card getCard(int x, int y) {
        return cards[x][y];
    }

    @Override
    public void applyCard(Location location, Card card) {
        cards[location.getX()][location.getY()] = card;
        bounds.updateMinMax(location.getX(), location.getY());
    }

    @Override
    public Card[][] getCards() {
        return cards;
    }

    @Override
    public BoardBounds getBounds() {
        return bounds;
    }

}

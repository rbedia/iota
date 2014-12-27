package org.doxu.iota;

public interface BoardView {
    void init();
    Location findCard(Card card);
    Card getCard(Location location);
    Card getCard(int x, int y);
    void applyCard(Location location, Card card);
    Card[][] getCards();
    BoardBounds getBounds();
}

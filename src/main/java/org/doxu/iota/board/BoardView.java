package org.doxu.iota.board;

import org.doxu.iota.Card;
import org.doxu.iota.Location;

public interface BoardView {
    void init();
    Location findCard(Card card);
    Card getCard(Location location);
    Card getCard(int x, int y);
    void applyCard(Location location, Card card);
    BoardBounds getBounds();
}

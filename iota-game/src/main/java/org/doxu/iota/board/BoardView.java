package org.doxu.iota.board;

import org.doxu.iota.Card;
import org.doxu.iota.Location;

public interface BoardView {
    void init();
    Location findCard(Card card);
    Card getCard(Location location);
    void applyCard(Location location, Card card);
    BoardBounds getBounds();
}

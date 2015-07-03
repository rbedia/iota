package org.doxu.iota.board;

import org.doxu.iota.Card;
import org.doxu.iota.Location;

public interface BoardView {

    void init();

    /**
     * Returns the location of the card on the board or null if the card isn't
     * on the board.
     *
     * @param card Card o search for.
     * @return Location of the card or null if it isn't on the board.
     */
    Location findCard(Card card);

    Card getCard(Location location);

    void applyCard(Location location, Card card);

    BoardBounds getBounds();
}

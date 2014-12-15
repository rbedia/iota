package org.doxu.iota.turn;

import org.doxu.iota.Game;

/**
 *
 * @author rafael
 */
public interface Turn {
    void execute();
    void setGame(Game game);
}

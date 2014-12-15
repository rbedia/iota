package org.doxu.iota.turn;

import org.doxu.iota.Game;
import org.doxu.iota.Player;

/**
 *
 * @author rafael
 */
public abstract class BaseTurn implements Turn {

    protected Game game;

    protected final Player player;

    public BaseTurn(Player player) {
        this.player = player;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

}

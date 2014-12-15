package org.doxu.iota.turn;

import org.doxu.iota.Laydown;
import org.doxu.iota.Player;

public class PassTurn extends BaseTurn {

    public PassTurn(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        game.pass(player);
    }

}

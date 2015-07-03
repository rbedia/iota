package org.doxu.iota.turn;

import org.doxu.iota.Game;
import org.doxu.iota.Player;
import org.doxu.iota.player.ScoreLaydown;

public class PassTurn extends BaseTurn {

    @Override
    public ScoreLaydown execute(Game game, Player player) {
        game.pass(player);
        return noScoreLaydown();
    }

}

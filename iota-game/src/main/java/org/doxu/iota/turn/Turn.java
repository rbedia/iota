package org.doxu.iota.turn;

import org.doxu.iota.Game;
import org.doxu.iota.Player;
import org.doxu.iota.player.ScoreLaydown;

public interface Turn {
    ScoreLaydown execute(Game game, Player player);
}

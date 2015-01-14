package org.doxu.iota.turn;

import org.doxu.iota.Game;
import org.doxu.iota.player.ScoreLaydown;

public interface Turn {
    void execute();
    ScoreLaydown log();
    void setGame(Game game);
}

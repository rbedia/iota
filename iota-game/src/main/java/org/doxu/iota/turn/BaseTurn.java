package org.doxu.iota.turn;

import org.doxu.iota.Laydown;
import org.doxu.iota.player.ScoreLaydown;

public abstract class BaseTurn implements Turn {

    protected ScoreLaydown noScoreLaydown() {
        return new ScoreLaydown(0, new Laydown());
    }
}

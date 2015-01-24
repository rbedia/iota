package org.doxu.iota.turn;

import org.doxu.iota.Laydown;
import org.doxu.iota.Player;
import org.doxu.iota.player.ScoreLaydown;

public abstract class BaseTurn implements Turn {

    protected final Player player;

    protected ScoreLaydown turnLog;

    public BaseTurn(Player player) {
        this.player = player;
    }

    protected void setTurnLog(ScoreLaydown laydown) {
        this.turnLog = laydown;
    }

    protected ScoreLaydown noScoreLaydown() {
        return new ScoreLaydown(0, new Laydown());
    }
}

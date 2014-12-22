package org.doxu.iota.turn;

import org.doxu.iota.Game;
import org.doxu.iota.Laydown;
import org.doxu.iota.Player;
import org.doxu.iota.player.ScoreLaydown;

public abstract class BaseTurn implements Turn {

    protected Game game;

    protected final Player player;

    protected ScoreLaydown turnLog;

    public BaseTurn(Player player) {
        this.player = player;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    protected void setTurnLog(ScoreLaydown laydown) {
        this.turnLog = laydown;
    }

    protected void setNoScoreLog() {
        turnLog = new ScoreLaydown(0, new Laydown());
    }

    @Override
    public ScoreLaydown log() {
        return turnLog;
    }

}

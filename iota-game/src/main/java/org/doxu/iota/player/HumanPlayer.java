package org.doxu.iota.player;

import org.doxu.iota.Player;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.doxu.iota.turn.PassTurn;
import org.doxu.iota.turn.Turn;

public class HumanPlayer extends Player {

    private final SynchronousQueue<Turn> next;

    public HumanPlayer() {
        super(true);
        setName("human");
        next = new SynchronousQueue<>();
    }

    @Override
    public Turn turn() {
        try {
            return next.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(HumanPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new PassTurn(this);
    }

    public void playTurn(Turn turn) {
        next.add(turn);
    }
}

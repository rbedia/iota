package org.doxu.iota.player;

import org.doxu.iota.Laydown;

public class ScoreLaydown implements Comparable<ScoreLaydown> {
    public final int score;
    public final Laydown laydown;

    public static final ScoreLaydown NO_SCORE = new ScoreLaydown(0, new Laydown());

    public ScoreLaydown(int score, Laydown laydown) {
        this.score = score;
        this.laydown = laydown;
    }

    @Override
    public int compareTo(ScoreLaydown o) {
        return o.score == score ? 0 : o.score < score ? -1 : 1;
    }

}

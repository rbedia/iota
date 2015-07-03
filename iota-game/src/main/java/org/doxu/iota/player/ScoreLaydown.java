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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.score;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScoreLaydown other = (ScoreLaydown) obj;
        return this.score == other.score;
    }

}

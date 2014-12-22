package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.doxu.iota.player.ScoreLaydown;

public class GameLog {

    private final List<Round> rounds;

    private final int playerCount;

    public GameLog(int playerCount) {
        rounds = new ArrayList<>();
        rounds.add(new Round(playerCount));
        this.playerCount = playerCount;
    }

    public void addLaydown(ScoreLaydown laydown) {
        Round round = rounds.get(rounds.size() - 1);
        if (round.getIndex() == playerCount) {
            round = new Round(playerCount);
            rounds.add(round);
        }
        round.addLaydown(laydown);
    }

    public List<Round> getRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public int getPlayers() {
        return playerCount;
    }

    public ScoreLaydown getScoreLaydown(int roundIndex, int playerIndex) {
        if (roundIndex >= rounds.size()) {
            return ScoreLaydown.NO_SCORE;
        }
        Round round = rounds.get(roundIndex);
        if (playerIndex >= round.getLaydowns().length) {
            return ScoreLaydown.NO_SCORE;
        }
        ScoreLaydown laydown = round.getLaydowns()[playerIndex];
        if (laydown == null) {
            return ScoreLaydown.NO_SCORE;
        }
        return laydown;
    }

    public int getScore(int roundIndex, int playerIndex) {
        return getScoreLaydown(roundIndex, playerIndex).score;
    }

    public class Round {

        private final ScoreLaydown[] laydowns;

        private int index;

        public Round(int players) {
            laydowns = new ScoreLaydown[players];
            index = 0;
        }

        public void addLaydown(ScoreLaydown laydown) {
            laydowns[index] = laydown;
            index++;
        }

        public ScoreLaydown[] getLaydowns() {
            return laydowns;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < laydowns.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(laydowns[i].score);
            }
            return sb.toString();
        }
    }
}

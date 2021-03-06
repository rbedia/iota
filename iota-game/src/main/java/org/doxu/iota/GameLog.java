package org.doxu.iota;

import java.util.ArrayList;
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

    public int getRounds() {
        return rounds.size();
    }

    public int getPlayers() {
        return playerCount;
    }

    public Laydown getLastTurn() {
        Round round = rounds.get(rounds.size() - 1);
        return round.getLast().laydown;
    }

    public ScoreLaydown getScoreLaydown(int roundIndex, int playerIndex) {
        if (roundIndex >= rounds.size() || roundIndex < 0) {
            return ScoreLaydown.NO_SCORE;
        }
        Round round = rounds.get(roundIndex);
        if (playerIndex >= round.getLaydowns().length || playerIndex < 0) {
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

    private static class Round {

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

        public ScoreLaydown getLast() {
            if (index > 0) {
                return laydowns[index - 1];
            }
            return ScoreLaydown.NO_SCORE;
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

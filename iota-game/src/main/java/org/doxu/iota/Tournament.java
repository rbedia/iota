package org.doxu.iota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.doxu.iota.player.MinimizerPlayer;
import org.doxu.iota.player.RandomPlayer;
import org.doxu.iota.player.SimpleHighFourPlayer;
import org.doxu.iota.player.SimpleHighThreePlayer;

public class Tournament {

    public void run(List<Player> players, int games) {
        Map<Player, PlayerWins> wins = new HashMap<>();
        for (Player player : players) {
            wins.put(player, new PlayerWins(player));
        }

        for (int i = 0; i < games; i++) {
            Game game = new Game();
            Collections.shuffle(players);
            game.init(players);
            game.deal();
            game.playStartingCard();
            while (!game.step()) {
            }
            Player winner = game.getWinner();
            PlayerWins playerWins = wins.get(winner);
            playerWins.wins++;
        }

        for (PlayerWins playerWins : wins.values()) {
            Player player = playerWins.player;
            int win = playerWins.wins;
            System.out.println("Player " + player.getName() + " wins: " + win);
        }
    }

    private static class PlayerWins {

        int wins;

        Player player;

        public PlayerWins(Player player) {
            this.player = player;
            this.wins = 0;
        }
    }

    public static void main(String[] args) {
        Tournament tournament = new Tournament();

        List<Player> players = new ArrayList<>();

        players.add(new SimpleHighFourPlayer());
        players.add(new SimpleHighThreePlayer());
        players.add(new MinimizerPlayer());
        players.add(new RandomPlayer());

        int games = 1000;

        tournament.run(players, games);
    }
}

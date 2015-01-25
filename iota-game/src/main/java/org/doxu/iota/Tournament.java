package org.doxu.iota;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.player.SimpleHighFourPlayer;
import org.doxu.iota.player.SimpleHighThreePlayer;
import org.doxu.iota.player.SimpleHighTwoPlayer;

public class Tournament {

    public void run(List<Player> players, int games) {
        int[] wins = new int[players.size()];

        for (int i = 0; i < games; i++) {
            Game game = new Game();
            // TODO switch the order of players
            game.init(players);
            game.deal();
            game.playStartingCard();
            while (!game.step()) {

            }
            Player winner = game.getWinner();
            int index = players.indexOf(winner);
            wins[index]++;
        }

        for (int i = 0; i < wins.length; i++) {
            Player player = players.get(i);
            int win = wins[i];
            System.out.println("Player " + player.getDisplayName() + " wins: " + win);
        }
    }

//    private static class Results {
//        Map<Player, Integer> wins = new HashMap<>();
//    }
    public static void main(String[] args) {
        Tournament tournament = new Tournament();

        List<Player> players = new ArrayList<>();

        players.add(new SimpleHighFourPlayer());
        players.add(new SimpleHighThreePlayer());
        players.add(new SimpleHighTwoPlayer());

        int games = 1000;

        tournament.run(players, games);
    }
}

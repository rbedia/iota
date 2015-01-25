package org.doxu.iota;

import org.doxu.iota.player.SimpleHighPlayer;
import org.doxu.iota.player.RandomPlayer;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public void start() {
        List<Player> players = new ArrayList<>();

        players.add(new RandomPlayer());
        players.add(new SimpleHighPlayer());
        players.add(new SimpleHighPlayer());

        Game game = new Game();
        game.init(players);
//        game.printDeck();
        game.deal();
        game.printHands();
        game.playStartingCard();
        while (!game.step()) {
            if (game.isEndOfRound()) {
                game.printEndOfRound();
            }
        }
        game.printHands();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}

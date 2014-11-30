package org.doxu.iota;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class Main {

    public void start() {
        List<Player> players = new ArrayList<>();

        players.add(new RandomPlayer());
        players.add(new SimpleHighPlayer());
        players.add(new SimpleHighPlayer());

        Game game = new Game(players);
//        game.printDeck();
        game.deal();
        game.printHands();
        game.playStartingCard();
        game.play();
        game.printHands();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}

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
        
        Player player1 = new SimpleHighPlayer();
        player1.setName("1 simple high");
        players.add(player1);

//        Player player1 = new RandomPlayer();
//        player1.setName("1 random");
//        players.add(player1);

        Player player2 = new SimpleHighPlayer();
        player2.setName("2 simple high");
        players.add(player2);

//        Player player2 = new RandomPlayer();
//        player2.setName("2 random");
//        players.add(player2);

//        Player player3 = new RandomPlayer();
//        player3.setName("3 random");
//        players.add(player3);

        Player player3 = new SimpleHighPlayer();
        player3.setName("3 simple high");
        players.add(player3);

        Game game = new Game(players);
        game.printDeck();
        game.deal();
        game.printHands();
        game.playStartingCard();
        game.play();
//        System.out.println("Final:");
//        game.printBoard();
        game.printHands();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}

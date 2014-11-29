package org.doxu.iota;

/**
 *
 * @author rafael
 */
public class Main {

    public void start() {
        Game game = new Game(2);
        game.printDeck();
        game.deal();
        game.printHands();
        game.playStartingCard();
        game.play();
        System.out.println("Final:");
        game.printBoard();
        game.printHands();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}

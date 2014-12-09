package org.doxu.iota.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.doxu.iota.Game;
import org.doxu.iota.Player;
import org.doxu.iota.RandomPlayer;
import org.doxu.iota.SimpleHighPlayer;

/**
 *
 * @author rafael
 */
public class UI extends JFrame {

    private Game game;

    private Table table;

    public UI() {
        init();
    }

    private void init() {
        setTitle("iota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Player> players = new ArrayList<>();

        players.add(new RandomPlayer());
        players.add(new SimpleHighPlayer());
        players.add(new SimpleHighPlayer());

        game = new Game(players);
        table = new Table(game.getBoard());

        add(table);

        setSize(800, 800);
        setLocationRelativeTo(null);

    }

    public void start() {
        game.deal();
        game.printHands();
        game.playStartingCard();
        game.play(table);
        game.printHands();
    }

    public static void main(String[] args) {

        final UI ui = new UI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.setVisible(true);
            }
        });
        ui.start();
    }
}

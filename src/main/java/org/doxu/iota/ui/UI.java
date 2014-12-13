package org.doxu.iota.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.doxu.iota.Game;
import org.doxu.iota.Laydown;
import org.doxu.iota.PlayListener;
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

    private List<PlayerPanel> playerPanels;

    private List<Player> players;

    public UI() {
        init();
    }

    private void init() {
        setTitle("iota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        players = new ArrayList<>();

        players.add(new RandomPlayer());
        players.add(new SimpleHighPlayer());
        players.add(new SimpleHighPlayer());

        playerPanels = new ArrayList<>();
        for (Player player : players) {
            playerPanels.add(new PlayerPanel(game, player));
        }

        game = new Game(players);
        table = new Table(game);

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.add(table);
        for (PlayerPanel playerPanel : playerPanels) {
            mainPane.add(playerPanel);
        }
        add(mainPane, BorderLayout.CENTER);
        JPanel menu = new MenuPanel(this);
        add(menu, BorderLayout.LINE_END);

        setSize(800, 800);
        setLocationRelativeTo(null);

    }

    private Thread gameThread;

    public void restart() {
        gameThread = new Thread(new UI.GameRunnable());
        gameThread.start();
    }

    public void start() {
        game.reset(players);
        game.deal();
        game.printHands();
        game.playStartingCard();
        repaint();
        game.play(new PlayListener() {

            @Override
            public void turn(Player player, Laydown laydown) {
                repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        game.printHands();
    }

    public class GameRunnable implements Runnable {

        @Override
        public void run() {
            start();
        }

    }

    public static void main(String[] args) {

        final UI ui = new UI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.setVisible(true);
            }
        });
    }
}

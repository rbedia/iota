package org.doxu.iota.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.doxu.iota.Game;
import org.doxu.iota.Player;
import org.doxu.iota.player.RandomPlayer;
import org.doxu.iota.player.SimpleHighPlayer;

public class UI extends JFrame {

    private Game game;

    private Table table;

    private JPanel playersPane;

    public UI() {
        init();
    }

    private void init() {
        setTitle("iota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game();
        table = new Table(game.getBoard());

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.add(table);
        playersPane = new JPanel();
        playersPane.setLayout(new BoxLayout(playersPane, BoxLayout.PAGE_AXIS));
        playersPane.setMinimumSize(new Dimension(500, 200));
        playersPane.setPreferredSize(new Dimension(500, 200));
        add(playersPane, BorderLayout.PAGE_END);
        add(mainPane, BorderLayout.CENTER);
        JPanel menu = new MenuPanel(this);
        add(menu, BorderLayout.LINE_END);

        setSize(800, 800);
        setLocationRelativeTo(null);
    }

    private Thread gameThread;

    public void restart(List<String> playerTypes) {
        List<Player> players = new ArrayList<>();
        for (String playerType : playerTypes) {
            switch (playerType) {
                case "Random":
                    players.add(new RandomPlayer());
                    break;
                case "Simple High":
                    players.add(new SimpleHighPlayer());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown player type: " + playerType);
            }
        }
        playersPane.removeAll();
        for (Player player : players) {
            playersPane.add(new PlayerPanel(player));
        }
        playersPane.revalidate();
        gameThread = new Thread(new GameRunnable(players));
        gameThread.start();
    }

    public class GameRunnable implements Runnable {

        List<Player> players;

        public GameRunnable(List<Player> players) {
            this.players = players;
        }

        @Override
        public void run() {
            start(players);
        }

    }

    public void start(List<Player> players) {
        game.init(players);
        game.deal();
        game.printHands();
        game.playStartingCard();
        repaint();
        while (!game.step()) {
            repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        repaint();
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
    }
}

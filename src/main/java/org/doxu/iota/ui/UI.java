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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import org.doxu.iota.Game;
import org.doxu.iota.Player;
import org.doxu.iota.player.RandomPlayer;
import org.doxu.iota.player.ScoreLaydown;
import org.doxu.iota.player.SimpleHighFourPlayer;
import org.doxu.iota.player.SimpleHighPlayer;
import org.doxu.iota.player.SimpleHighThreePlayer;
import org.doxu.iota.player.SimpleHighTwoPlayer;

public class UI extends JFrame {

    private Game game;

    private Table table;

    private JPanel playersPane;

    private JTable logTable;

    public UI() {
        init();
    }

    private void init() {
        setTitle("iota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game();
        table = new Table(game);

        playersPane = new JPanel();
        playersPane.setLayout(new BoxLayout(playersPane, BoxLayout.PAGE_AXIS));
        playersPane.setPreferredSize(new Dimension(400, 160));
        JPanel sidePane = new JPanel();
        sidePane.setLayout(new BoxLayout(sidePane, BoxLayout.PAGE_AXIS));
        sidePane.add(playersPane);
        logTable = createLogTable();
        JScrollPane logTableScroll = new JScrollPane(logTable);
        sidePane.add(logTableScroll);
        add(sidePane, BorderLayout.LINE_END);
        add(table, BorderLayout.CENTER);
        JPanel menu = new MenuPanel(this);
        add(menu, BorderLayout.LINE_START);

        setSize(1350, 850);
        setLocationRelativeTo(null);
    }

    private JTable createLogTable() {
        JTable jTable = new JTable(new GameLogModel(game));
        jTable.setPreferredScrollableViewportSize(new Dimension(400, 600));
        jTable.setFillsViewportHeight(true);
        jTable.setCellSelectionEnabled(true);
        jTable.setDefaultRenderer(ScoreLaydown.class, new ScoreLaydownRenderer());
        jTable.setRowHeight(CardRenderer.CARD_WIDTH + CardRenderer.INSET);

        GameLogSelectionListener selListener = new GameLogSelectionListener(jTable, table);
        jTable.getSelectionModel().addListSelectionListener(selListener);
        jTable.getColumnModel().getSelectionModel().
                addListSelectionListener(selListener);
        return jTable;
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
                case "Simple High 2":
                    players.add(new SimpleHighTwoPlayer());
                    break;
                case "Simple High 3":
                    players.add(new SimpleHighThreePlayer());
                    break;
                case "Simple High 4":
                    players.add(new SimpleHighFourPlayer());
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
        ((GameLogModel) logTable.getModel()).init();
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

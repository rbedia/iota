package org.doxu.iota.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import org.doxu.iota.Card;
import org.doxu.iota.Game;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.doxu.iota.Player;
import org.doxu.iota.board.Board;
import org.doxu.iota.player.HumanPlayer;
import org.doxu.iota.player.RandomPlayer;
import org.doxu.iota.player.ScoreLaydown;
import org.doxu.iota.player.SimpleHighFourPlayer;
import org.doxu.iota.player.SimpleHighPlayer;
import org.doxu.iota.player.SimpleHighThreePlayer;
import org.doxu.iota.player.SimpleHighTwoPlayer;
import org.doxu.iota.turn.LaydownTurn;
import org.doxu.iota.turn.PassTurn;

public class UI extends JFrame implements LocationListener {

    private Game game;

    private Table table;

    private JPanel playersPane;

    private final Map<Player, PlayerPanel> playerPanels;

    private JTable logTable;

    private final Laydown pendingLaydown;

    public UI() {
        pendingLaydown = new Laydown();
        playerPanels = new HashMap<>();
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

        table.addClickListener(this);
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
                case "Human":
                    players.add(new HumanPlayer());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown player type: " + playerType);
            }
        }
        playersPane.removeAll();
        for (Player player : players) {
            PlayerPanel panel = new PlayerPanel(player);
            playersPane.add(panel);
            playerPanels.put(player, panel);
        }
        playersPane.revalidate();
        gameThread = new Thread(new GameRunnable(players));
        gameThread.start();
    }

    @Override
    public void locationClicked(Location location) {
        // get current player
        Player player = game.getCurrentPlayer();
        PlayerPanel panel = playerPanels.get(player);
        Card selectedCard = panel.getRack().getSelection();
        if (selectedCard.isBlank()) {
            return;
        }
        Laydown testLaydown = pendingLaydown.copy();
        Move testMove = new Move(location, selectedCard);
        testLaydown.addMove(testMove);
        // make copy of board and try to place card
        Board testBoard = game.getBoard().overlay();
        try {
            testBoard.applyLaydown(testLaydown);
            // if position is valid add to current player's move list
            pendingLaydown.addMove(testMove);
            table.setBoard(testBoard);
            table.setNextTurn(pendingLaydown.getLocations());
            System.out.println("Moves: " + pendingLaydown.getMoves().size());
        } catch (IllegalLaydownException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void submitTurn() {
        Player player = game.getCurrentPlayer();
        if (!player.isHuman()) {
            return;
        }
        HumanPlayer human = (HumanPlayer) player;
        Laydown laydown = pendingLaydown.copy();
        pendingLaydown.clear();
        table.resetBoard();
        table.setNextTurn(Collections.EMPTY_LIST);
        // TODO clear rack selection?
        if (laydown.getMoves().isEmpty()) {
            human.playTurn(new PassTurn(player));
        } else {
            human.playTurn(new LaydownTurn(laydown, player));
        }
        repaint();
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

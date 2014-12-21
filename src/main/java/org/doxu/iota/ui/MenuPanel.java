package org.doxu.iota.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuPanel extends JPanel {

    private static final int MAX_PLAYERS = 4;
    private static final int DEFAULT_PLAYERS = 3;
    private static final int MIN_PLAYERS = 2;

    private final JButton start;

    private final JPanel playerPanel;

    public MenuPanel(final UI ui) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        start = new JButton("Start");
        start.setMnemonic(KeyEvent.VK_S);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                start.setEnabled(false);
                ui.restart(getPlayerTypes());
            }
        });
        SpinnerModel spinnerModel = new SpinnerNumberModel(DEFAULT_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setMaximumSize(new Dimension(60, 45));
        spinnerModel.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel model = (SpinnerNumberModel) e.getSource();
                int players = model.getNumber().intValue();
                setVisiablePlayers(players);
            }
        });
        playerPanel = createPlayerPanel(MAX_PLAYERS);
        setVisiablePlayers(DEFAULT_PLAYERS);
        JPanel playerSelector = new JPanel();
        playerSelector.add(new JLabel("Players:"));
        playerSelector.add(spinner);
        add(start);
        add(playerSelector);
        add(playerPanel);
        add(Box.createGlue());
    }

    private void setVisiablePlayers(int players) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            playerPanel.getComponent(i).setVisible(i < players);
        }
    }

    public List<String> getPlayerTypes() {
        List<String> types = new LinkedList<>();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            JComboBox combo = (JComboBox) playerPanel.getComponent(i);
            if (combo.isVisible()) {
                types.add((String) combo.getSelectedItem());
            }
        }
        return types;
    }

    public final JPanel createPlayerPanel(int players) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < players; i++) {
            panel.add(createPlayerCombo());
        }
        return panel;
    }

    public JComboBox createPlayerCombo() {
        JComboBox combo = new JComboBox(new String[]{"Simple High 4", "Simple High 3", "Simple High 2", "Simple High", "Random"});
        combo.setMaximumSize(new Dimension(120, 30));
        return combo;
    }
}

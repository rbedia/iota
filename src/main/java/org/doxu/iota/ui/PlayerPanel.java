package org.doxu.iota.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.doxu.iota.Player;

/**
 * Display for a player.
 */
public class PlayerPanel extends JPanel {

    private final Player player;
    
    private final JLabel name;
    private final JLabel score;
    private final Rack rack;

    private static final int NAME_WIDTH = 150;
    private static final int NAME_HEIGHT = 35;
    private static final int SCORE_WIDTH = 50;
    private static final int SCORE_HEIGHT = 35;

    public PlayerPanel(Player player) {
        this.player = player;
        setLayout(new FlowLayout());
        name = new JLabel(player.getDisplayName());
        name.setPreferredSize(new Dimension(NAME_WIDTH, NAME_HEIGHT));
        rack = new Rack(player);
        score = new JLabel("" + Integer.toString(player.getScore()));
        score.setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
        add(name);
        add(score);
        add(rack);

        Dimension dim = rack.getPreferredSize();
        int padding = 50;
        dim.width += name.getPreferredSize().getWidth();
        dim.width += score.getPreferredSize().getWidth();
        dim.width += padding;
        dim.height = (int) Math.max(dim.getHeight(), name.getPreferredSize().getHeight());
        setPreferredSize(dim);
        setMaximumSize(dim);

        if (player.isHuman()) {
            rack.addCardListener();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        name.setText(player.getDisplayName());
        score.setText(Integer.toString(player.getScore()));
    }

    public Rack getRack() {
        return rack;
    }
}

package org.doxu.iota.ui;

import javax.swing.table.DefaultTableCellRenderer;
import org.doxu.iota.player.ScoreLaydown;

public class ScoreLaydownRenderer extends DefaultTableCellRenderer {

    @Override
    public void setValue(Object value) {
        if (value != null) {
            ScoreLaydown scoreLaydown = (ScoreLaydown) value;
            setText("" + scoreLaydown.score);
        } else {
            setText("0");
        }
    }
}

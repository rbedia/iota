package org.doxu.iota.ui;

import java.util.Collections;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.doxu.iota.Location;
import org.doxu.iota.player.ScoreLaydown;

public class GameLogSelectionListener implements ListSelectionListener {

    private final JTable gameLog;
    private final Table table;

    public GameLogSelectionListener(JTable gameLog, Table table) {
        this.gameLog = gameLog;
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }
        int row = gameLog.getSelectionModel().getLeadSelectionIndex();
        int column = gameLog.getColumnModel().getSelectionModel().getLeadSelectionIndex();
        Object selection = gameLog.getModel().getValueAt(row, column);
        if (selection instanceof ScoreLaydown) {
            ScoreLaydown scoreLaydown = (ScoreLaydown) selection;
            table.setSelection(scoreLaydown.laydown.getLocations());
        } else {
            table.setSelection(Collections.<Location>emptyList());
        }
    }

}

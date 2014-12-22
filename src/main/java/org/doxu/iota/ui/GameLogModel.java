package org.doxu.iota.ui;

import javax.swing.table.AbstractTableModel;
import org.doxu.iota.Game;
import org.doxu.iota.GameLog;

public class GameLogModel extends AbstractTableModel {

    private final Game game;

    public GameLogModel(Game game) {
        this.game = game;
    }

    @Override
    public int getRowCount() {
        return getGameLog().getRounds().size();
    }

    @Override
    public int getColumnCount() {
        // Column for each player plus the round number
        return getGameLog().getPlayers() + 1;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Round";
        } else {
            return "Player " + column;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex + 1;
        }
        return getGameLog().getScore(rowIndex, columnIndex - 1);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    private GameLog getGameLog() {
        return game.getGameLog();
    }

    void init() {
        fireTableStructureChanged();
    }
}

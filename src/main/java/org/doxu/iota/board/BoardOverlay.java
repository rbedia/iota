package org.doxu.iota.board;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.Location;
import org.doxu.iota.Move;

public class BoardOverlay implements BoardView {

    private final BoardView board;

    private List<Move> moves;

    public BoardOverlay(BoardView board) {
        this.board = board;
        init();
    }

    public BoardOverlay(BoardView board, List<Move> moves) {
        this.board = board;
        this.moves = moves;
    }

    @Override
    public final void init() {
        // TODO decide if init() should propagate. For now it doesn't.
//        board.init();
        moves = new ArrayList<>();
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    @Override
    public Location findCard(Card card) {
        for (Move move : moves) {
            if (move.getCard().equals(card)) {
                return move.getLocation();
            }
        }
        return board.findCard(card);
    }

    @Override
    public Card getCard(Location location) {
        for (Move move : moves) {
            if (move.getLocation().equals(location)) {
                return move.getCard();
            }
        }
        return board.getCard(location);
    }

    @Override
    public Card getCard(int x, int y) {
        return getCard(new Location(x, y));
    }

    @Override
    public void applyCard(Location location, Card card) {
        moves.add(new Move(location, card));
    }

    @Override
    public BoardBounds getBounds() {
        // TODO calculate bounds from moves list
        return board.getBounds();
    }

}

package org.doxu.iota.board;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.Location;
import org.doxu.iota.Move;

public class BoardOverlay implements BoardView {

    private final BoardView board;

    private final List<Move> moves;

    private final BoardBounds bounds;

    public BoardOverlay(BoardView board) {
        this.board = board;
        moves = new ArrayList<>();
        bounds = new BoardBounds();
    }

    public BoardOverlay(BoardView board, List<Move> moves) {
        this(board);
        setMoves(moves);
    }

    @Override
    public final void init() {
        bounds.init();
        moves.clear();
    }

    public final void setMoves(List<Move> moves) {
        bounds.init();
        this.moves.clear();
        this.moves.addAll(moves);
        for (Move move : moves) {
            bounds.updateMinMax(move.getLocation().getX(), move.getLocation().getY());
        }
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
    public void applyCard(Location location, Card card) {
        moves.add(new Move(location, card));
        bounds.updateMinMax(location.getX(), location.getY());
    }

    @Override
    public BoardBounds getBounds() {
        BoardBounds parent = board.getBounds();
        BoardBounds combined = bounds.copy();
        combined.updateMinMax(parent.getMinX(), parent.getMinY());
        combined.updateMinMax(parent.getMaxX(), parent.getMaxY());
        return combined;
    }

}

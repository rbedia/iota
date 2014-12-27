package org.doxu.iota;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.doxu.iota.attr.LotValidator;
import org.doxu.iota.board.BoardBaseListener;
import org.doxu.iota.board.BoardLexer;
import org.doxu.iota.board.BoardParser;

public class Board {

    public static final int BOARD_SIZE = 127;

    public static final int MIDDLE = (BOARD_SIZE + 1) / 2;

    private final BoardView board;

    public Board() {
        this(new BoardBase());
    }

    public Board(BoardView board) {
        this.board = board;
    }

    public final void init() {
        board.init();
    }

    public Board overlay() {
        return new Board(new BoardOverlay(board));
    }

    public Board overlay(List<Move> moves) {
        return new Board(new BoardOverlay(board, moves));
    }

    public Card getCard(Location location) {
        return board.getCard(location);
    }

    public Card getCard(int x, int y) {
        return board.getCard(x, y);
    }

    public BoardBounds getBounds() {
        return board.getBounds();
    }

    public Location findCard(Card card) {
        return board.findCard(card);
    }

    public void load(String input) throws IOException, IllegalLaydownException {
        load(new StringReader(input));
    }

    public void load(Reader reader) throws IOException, IllegalLaydownException {
        BoardLexer l = new BoardLexer(new ANTLRInputStream(reader));
        BoardParser p = new BoardParser(new CommonTokenStream(l));
        p.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });
        p.addParseListener(new BoardBaseListener() {
            int row = 0;
            int column = 0;
            int maxX = 0;
            Card[][] cards = new Card[BOARD_SIZE][BOARD_SIZE];

            @Override
            public void exitRow(BoardParser.RowContext ctx) {
                maxX = column > maxX ? column : maxX;
                row++;
                column = 0;
            }

            @Override
            public void exitCell(BoardParser.CellContext ctx) {
                cards[column][row] = Card.create(ctx.getText());
                column++;
            }

            @Override
            public void exitBoard(BoardParser.BoardContext ctx) {
                int xOffset = (BOARD_SIZE - maxX) / 2;
                int yOffset = (BOARD_SIZE - row) / 2;
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < maxX; j++) {
                        board.applyCard(new Location(xOffset + j, yOffset + i), cards[j][i]);
                    }
                }
            }
        });
        p.board();
        validateBoard();
    }

    public int applyLaydown(Laydown laydown) throws IllegalLaydownException {
        validLaydown(laydown);
        List<Move> moves = laydown.getMoves();
        for (Move move : moves) {
            board.applyCard(move.getLocation(), move.getCard());
        }
        return calculateScore(moves);
    }

    public void undo(Laydown laydown) {
        List<Move> moves = laydown.getMoves();
        for (Move move : moves) {
            board.applyCard(move.getLocation(), Card.BLANK);
        }
    }

    private int calculateScore(List<Move> moves) {
        int sum = 0;
        int lots = 0;
        Set<Card> hCounted = new HashSet<>();
        Set<Card> vCounted = new HashSet<>();
        for (Move move : moves) {
            int rowLength = 0;
            // search right
            Location location = move.getLocation().right();
            Card card = board.getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!hCounted.contains(card)) {
                    sum += card.getPoints();
                    hCounted.add(card);
                }
                location = location.right();
                card = board.getCard(location);
            }
            // search left
            location = move.getLocation().left();
            card = board.getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!hCounted.contains(card)) {
                    sum += card.getPoints();
                    hCounted.add(card);
                }
                location = location.left();
                card = board.getCard(location);
            }
            if (rowLength > 0 && !hCounted.contains(move.getCard())) {
                sum += move.getCard().getPoints();
                hCounted.add(move.getCard());
                rowLength++;
            }
            if (rowLength == 4) {
                lots++;
            }
            rowLength = 0;
            // search up
            location = move.getLocation().up();
            card = board.getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!vCounted.contains(card)) {
                    sum += card.getPoints();
                    vCounted.add(card);
                }
                location = location.up();
                card = board.getCard(location);
            }
            // search down
            location = move.getLocation().down();
            card = board.getCard(location);
            while (card != Card.BLANK) {
                rowLength++;
                if (!vCounted.contains(card)) {
                    sum += card.getPoints();
                    vCounted.add(card);
                }
                location = location.down();
                card = board.getCard(location);
            }
            if (rowLength > 0 && !vCounted.contains(move.getCard())) {
                sum += move.getCard().getPoints();
                vCounted.add(move.getCard());
                rowLength++;
            }
            if (rowLength == 4) {
                lots++;
            }
        }
        return sum * (1 << lots);
    }

    public void validLaydown(Laydown laydown) throws IllegalLaydownException {
        List<Move> moves = laydown.getMoves();
        boolean allowed = false;
        for (Move move : moves) {
            if (isTouchingBoard(move.getLocation())) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            throw new IllegalLaydownException("Laydown must touch existing cards.");
        }
        allowed = true;
        for (Move move : moves) {
            if (isOverlappingCard(move.getLocation())) {
                allowed = false;
                break;
            }
        }
        if (!allowed) {
            throw new IllegalLaydownException("Laydown overlaps existing cards.");
        }
        Board boardCopy = overlay(moves);
        boardCopy.validateBoard(laydown.getLocations());
    }

    public boolean isTouchingBoard(Location location) {
        int x = location.getX() - 1;
        int y = location.getY();
        if (isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank()) {
            return true;
        }
        x = location.getX() + 1;
        y = location.getY();
        if (isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank()) {
            return true;
        }
        x = location.getX();
        y = location.getY() - 1;
        if (isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank()) {
            return true;
        }
        x = location.getX();
        y = location.getY() + 1;
        return isInRange(x) && isInRange(y) && !board.getCard(new Location(x, y)).isBlank();
    }

    public boolean isOverlappingCard(Location location) {
        if (!isInRange(location.getX()) || !isInRange(location.getY())) {
            throw new IllegalArgumentException("Location is not on the board.");
        }
        return !board.getCard(location).isBlank();
    }

    private boolean isInRange(int x) {
        return x >= 0 && x < BOARD_SIZE;
    }

    private void validateBoard() throws IllegalLaydownException {
        // Validate rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<Card> lot = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                Card card = getCard(i, j);
                if (!card.isBlank()) {
                    lot.add(card);
                } else {
                    LotValidator.validate(lot);
                    lot.clear();
                }
            }
        }
        // Validate columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<Card> lot = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                Card card = getCard(j, i);
                if (!card.isBlank()) {
                    lot.add(card);
                } else {
                    LotValidator.validate(lot);
                    lot.clear();
                }
            }
        }
    }

    private List<Card> gatherRow(Location start) {
        List<Card> cardRow = new ArrayList<>();
        // search right
        Location location = start;
        Card card = getCard(location);
        while (!card.isBlank()) {
            cardRow.add(card);
            location = location.right();
            card = getCard(location);
        }
        // search left
        location = start.left();
        card = getCard(location);
        while (!card.isBlank()) {
            cardRow.add(card);
            location = location.left();
            card = getCard(location);
        }
        return cardRow;
    }

    private List<Card> gatherColumn(Location start) {
        List<Card> cardColumn = new ArrayList<>();
        // search up
        Location location = start;
        Card card = getCard(location);
        while (!card.isBlank()) {
            cardColumn.add(card);
            location = location.up();
            card = getCard(location);
        }
        // search down
        location = start.down();
        card = getCard(location);
        while (!card.isBlank()) {
            cardColumn.add(card);
            location = location.down();
            card = getCard(location);
        }
        return cardColumn;
    }

    private void validateBoard(List<Location> locations) throws IllegalLaydownException {
        // Validate rows
        for (Location location : locations) {
            List<Card> cards = gatherRow(location);
            LotValidator.validate(cards);
        }
        // Validate columns
        for (Location location : locations) {
            List<Card> cards = gatherColumn(location);
            LotValidator.validate(cards);
        }
    }

    public void print() {
        for (int y = board.getBounds().getMinY(); y < board.getBounds().getMaxY() + 1; y++) {
            for (int x = board.getBounds().getMinX(); x < board.getBounds().getMaxX() + 1; x++) {
                System.out.print(" " + board.getCard(new Location(x, y)));
            }
            System.out.println();
        }
    }

    public void addFirst(Card first) {
        Location location = new Location(MIDDLE, MIDDLE);
        board.applyCard(location, first);
    }
}

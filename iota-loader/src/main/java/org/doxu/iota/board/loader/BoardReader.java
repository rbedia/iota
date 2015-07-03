package org.doxu.iota.board.loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.doxu.iota.board.BoardBaseListener;
import org.doxu.iota.board.BoardLexer;
import org.doxu.iota.board.BoardParser;

public class BoardReader {

    private static final int INIT_SIZE = 64;

    public static String[][] read(String input) throws IOException {
        return read(new StringReader(input));
    }

    public static String[][] read(Reader reader) throws IOException {
        BoardLexer l = new BoardLexer(new ANTLRInputStream(reader));
        BoardParser p = new BoardParser(new CommonTokenStream(l));
        p.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });
        ParserListener listener = new ParserListener();
        p.addParseListener(listener);
        p.board();
        String[][] cards = listener.getCards();
        return cards;
    }

    private static class ParserListener extends BoardBaseListener {

        private int row = 0;
        private int column = 0;
        private int maxX = 0;
        private String[][] cards = new String[INIT_SIZE][INIT_SIZE];

        @Override
        public void exitRow(BoardParser.RowContext ctx) {
            maxX = column > maxX ? column : maxX;
            row++;
            column = 0;
        }

        @Override
        public void exitCell(BoardParser.CellContext ctx) {
            cards[row][column] = ctx.getText();
            column++;
        }

        @Override
        public void exitBoard(BoardParser.BoardContext ctx) {
            String[][] copy = new String[row][maxX];
            for (int i = 0; i < row; i++) {
                System.arraycopy(cards[i], 0, copy[i], 0, maxX);
                for (int j = 0; j < maxX; j++) {
                    if (copy[i][j] == null) {
                        copy[i][j] = "...";
                    }
                }
            }
            cards = copy;
        }

        public String[][] getCards() {
            return cards;
        }
    }
}

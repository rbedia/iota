package org.doxu.iota.board;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.Location;
import org.doxu.iota.Move;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardOverlayTest {

    BoardBase board;
    BoardOverlay overlay;

    @Before
    public void setUp() {
        board = new BoardBase();
        overlay = new BoardOverlay(board);
    }

    @Test
    public void testInit() {
        Location location = new Location(Board.MIDDLE, Board.MIDDLE);
        overlay.applyCard(location, Card.create("BS1"));
        overlay.init();
        assertEquals(Card.BLANK, overlay.getCard(location));
    }

    @Test
    public void testSetMoves() {
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("BS2")));
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE), Card.create("BS1"));
        overlay.setMoves(moves);
        Location location = new Location(Board.MIDDLE, Board.MIDDLE);
        assertEquals(Card.BLANK, overlay.getCard(location));
        Location location2 = new Location(Board.MIDDLE + 1, Board.MIDDLE);
        assertEquals(Card.create("BS2"), overlay.getCard(location2));
    }

    @Test
    public void testFindCard1() {
        Card card = Card.create("BS1");
        Location expResult = new Location(Board.MIDDLE, Board.MIDDLE);
        overlay.applyCard(expResult, card);
        Location result = overlay.findCard(card);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindCard2() {
        Location location = new Location(Board.MIDDLE, Board.MIDDLE);
        overlay.applyCard(location, Card.create("BS1"));
        Location result = overlay.findCard(Card.create("BS2"));
        assertNull(result);
    }

    @Test
    public void testFindCard3() {
        overlay.applyCard(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("BS2"));
        Card card = Card.create("BS1");
        Location expResult = new Location(Board.MIDDLE, Board.MIDDLE);
        board.applyCard(expResult, card);
        Location result = overlay.findCard(card);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCard1() {
        Location location = new Location(Board.MIDDLE, Board.MIDDLE);
        Card expResult = Card.create("BS1");
        overlay.applyCard(location, expResult);
        Card result = overlay.getCard(location);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCard2() {
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE), Card.create("BS1"));
        Location location = new Location(Board.MIDDLE + 1, Board.MIDDLE);
        Card expResult = Card.BLANK;
        Card result = overlay.getCard(location);
        assertEquals(expResult, result);
    }

    @Test
    public void testApplyCard() {
        Card card = Card.create("BS1");
        Location expResult = new Location(Board.MIDDLE, Board.MIDDLE);
        overlay.applyCard(expResult, card);
        Location result = overlay.findCard(card);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBounds1() {
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE), Card.create("BS1"));
        overlay.applyCard(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("BS2"));
        BoardBounds expResult = new BoardBounds(Board.MIDDLE, Board.MIDDLE, Board.MIDDLE + 1, Board.MIDDLE);
        BoardBounds result = overlay.getBounds();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBounds2() {
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE), Card.create("BS1"));
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("BS2"));
        BoardBounds expResult = new BoardBounds(Board.MIDDLE, Board.MIDDLE, Board.MIDDLE, Board.MIDDLE + 1);
        BoardBounds result = overlay.getBounds();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBounds3() {
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE), Card.create("BS1"));
        overlay.applyCard(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("BS2"));
        board.applyCard(new Location(Board.MIDDLE - 1, Board.MIDDLE), Card.create("BS3"));
        BoardBounds expResult = new BoardBounds(Board.MIDDLE - 1, Board.MIDDLE, Board.MIDDLE, Board.MIDDLE + 1);
        BoardBounds result = overlay.getBounds();
        assertEquals(expResult, result);
    }

}

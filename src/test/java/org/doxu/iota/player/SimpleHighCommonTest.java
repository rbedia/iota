package org.doxu.iota.player;

import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.Location;
import org.doxu.iota.board.Board;
import org.doxu.iota.board.BoardBase;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleHighCommonTest {

    @Test
    public void testCollectValidLocations_Board1() {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        List<Location> result = SimpleHighCommon.collectValidLocations(board);
        assertEquals(0, result.size());
    }

    @Test
    public void testCollectValidLocations_Board2() {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        Card card = Card.create("BS1");
        board.addFirst(card);
        List<Location> result = SimpleHighCommon.collectValidLocations(board);
        assertEquals(4, result.size());
        Location location = board.findCard(card);
        assertTrue(result.contains(location.left()));
        assertTrue(result.contains(location.right()));
        assertTrue(result.contains(location.down()));
        assertTrue(result.contains(location.up()));
    }

    @Test
    public void testCollectValidLocations_Board3() {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        Card card1 = Card.create("BS1");
        Card card2 = Card.create("BS2");
        Card card3 = Card.create("BS3");
        board.addFirst(card1);
        Location location1 = board.findCard(card1);
        Location location2 = location1.left();
        Location location3 = location2.up();
        base.applyCard(location2, card2);
        base.applyCard(location3, card3);
        List<Location> result = SimpleHighCommon.collectValidLocations(board);
        assertEquals(7, result.size());
        assertTrue(result.contains(location1.right()));
        assertTrue(result.contains(location1.down()));
        assertTrue(result.contains(location1.up()));
        assertTrue(result.contains(location2.left()));
        assertTrue(result.contains(location2.down()));
        assertTrue(result.contains(location3.up()));
        assertTrue(result.contains(location3.left()));
        assertTrue(result.contains(location3.right()));
    }

    @Test
    public void testCollectValidLocations_Board4() {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        Card card1 = Card.create("BS1");
        Card card2 = Card.create("BS2");
        Card card3 = Card.create("BS3");
        board.addFirst(card1);
        Location location1 = board.findCard(card1);
        Location location2 = location1.right();
        Location location3 = location2.down();
        base.applyCard(location2, card2);
        base.applyCard(location3, card3);
        List<Location> result = SimpleHighCommon.collectValidLocations(board);
        assertEquals(7, result.size());
        assertTrue(result.contains(location1.left()));
        assertTrue(result.contains(location1.down()));
        assertTrue(result.contains(location1.up()));
        assertTrue(result.contains(location2.right()));
        assertTrue(result.contains(location2.up()));
        assertTrue(result.contains(location3.down()));
        assertTrue(result.contains(location3.left()));
        assertTrue(result.contains(location3.right()));
    }
}

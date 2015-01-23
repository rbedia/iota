package org.doxu.iota.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Location;
import org.doxu.iota.board.Board;
import org.doxu.iota.board.BoardBase;
import org.doxu.iota.board.loader.BoardReader;
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

    @Test(expected = IllegalArgumentException.class)
    public void testCollectValidLocations0() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2\nBS4 BS3"));

        List<Location> playLocations = new ArrayList<>();
        SimpleHighCommon.collectValidLocations(board, playLocations);
    }

    @Test
    public void testCollectValidLocations1() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2\nBS4 BS3"));
        Location location1 = board.findCard(Card.create("BS1"));
        Location location3 = board.findCard(Card.create("BS3"));
        Location playLocation1 = board.findCard(Card.create("BS4"));

        List<Location> playLocations = Arrays.asList(playLocation1);
        List<Location> result = SimpleHighCommon.collectValidLocations(board, playLocations);
        assertEquals(4, result.size());
        assertTrue(result.contains(playLocation1.left()));
        assertTrue(result.contains(playLocation1.down()));
        assertTrue(result.contains(location1.up()));
        assertTrue(result.contains(location3.right()));
    }

    @Test
    public void testCollectValidLocations2() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2\nBS4 BS3"));
        Location location1 = board.findCard(Card.create("BS1"));
        Location location3 = board.findCard(Card.create("BS3"));
        Location playLocation2 = board.findCard(Card.create("BS2"));

        List<Location> playLocations = Arrays.asList(playLocation2);
        List<Location> result = SimpleHighCommon.collectValidLocations(board, playLocations);
        assertEquals(4, result.size());
        assertTrue(result.contains(playLocation2.up()));
        assertTrue(result.contains(playLocation2.right()));
        assertTrue(result.contains(location1.left()));
        assertTrue(result.contains(location3.down()));
    }

    @Test
    public void testCollectValidLocations3() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2\nBS4 BS3"));
        Location playLocation2 = board.findCard(Card.create("BS2"));
        Location playLocation3 = board.findCard(Card.create("BS3"));

        List<Location> playLocations = Arrays.asList(playLocation2, playLocation3);
        List<Location> result = SimpleHighCommon.collectValidLocations(board, playLocations);
        assertEquals(2, result.size());
        assertTrue(result.contains(playLocation2.up()));
        assertTrue(result.contains(playLocation3.down()));
    }

    @Test
    public void testCollectValidLocations4() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2\nBS4 BS3"));
        Location playLocation1 = board.findCard(Card.create("BS1"));
        Location playLocation2 = board.findCard(Card.create("BS2"));

        List<Location> playLocations = Arrays.asList(playLocation1, playLocation2);
        List<Location> result = SimpleHighCommon.collectValidLocations(board, playLocations);
        assertEquals(2, result.size());
        assertTrue(result.contains(playLocation1.left()));
        assertTrue(result.contains(playLocation2.right()));
    }

    @Test
    public void testCollectValidLocations5() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2 BS3 BS4"));
        Location location1 = board.findCard(Card.create("BS1"));
        Location playLocation1 = board.findCard(Card.create("BS4"));

        List<Location> playLocations = Arrays.asList(playLocation1);
        List<Location> result = SimpleHighCommon.collectValidLocations(board, playLocations);
        assertEquals(4, result.size());
        assertTrue(result.contains(playLocation1.up()));
        assertTrue(result.contains(playLocation1.down()));
        assertTrue(result.contains(playLocation1.right()));
        assertTrue(result.contains(location1.left()));
    }

    @Test
    public void testFindOptions0() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read(
                "BS1 BS2 BS3\n"
                + "GS1 GS2 GS3"));

        List<Card> hand = Arrays.asList(Card.create("Y+2"));
        List<ScoreLaydown> result = SimpleHighCommon.findOptions(hand, board);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindOptions1() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read("BS1 BS2\nBS3 ..."));

        List<Card> hand = Arrays.asList(Card.create("GS2"), Card.create("BO1"));
        List<ScoreLaydown> result = SimpleHighCommon.findOptions(hand, board);
        assertEquals(6, result.size());
    }

    @Test
    public void testFindOptions2() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read(
                "BS1 BS2 BS3\n"
                + "GS1 GS2 GS3"));

        List<Card> hand = Arrays.asList(Card.create("BS4"));
        List<ScoreLaydown> result = SimpleHighCommon.findOptions(hand, board);
        assertEquals(2, result.size());
        assertEquals(20, result.get(0).score);
        assertEquals(20, result.get(1).score);
    }

    @Test
    public void testFindOptions3() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read(
                "BS1 BS2 BS3\n"
                + "GS1 GS2 GS3"));

        List<Card> hand = Arrays.asList(Card.create("RS2"));
        List<ScoreLaydown> result = SimpleHighCommon.findOptions(hand, board);
        assertEquals(2, result.size());
        assertEquals(6, result.get(0).score);
        assertEquals(6, result.get(1).score);
    }

    @Test
    public void testFindOptions4() throws IOException, IllegalLaydownException {
        BoardBase base = new BoardBase();
        Board board = new Board(base);
        board.load(BoardReader.read(
                "BS1 BS2 ...\n"
                + "GS1 GS2 GS3"));

        List<Card> hand = new ArrayList<>();
        hand.add(Card.create("BS3"));
        List<ScoreLaydown> options1 = SimpleHighCommon.findOptions(hand, board);
        hand.add(Card.create("BS4"));
        List<ScoreLaydown> result = SimpleHighCommon.findOptions(hand, board, options1, false);
        assertEquals(6, result.size());
    }
}

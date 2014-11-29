package org.doxu.iota;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class BoardTest {

    Board board;

    public BoardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        board = new Board();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testScoring1Down() throws Exception {
        board.addFirst(Card.create("RS2"));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("R+3")));
        int score = board.applyLaydown(laydown);
        assertEquals(5, score);
    }

    @Test
    public void testScoring1Right() throws Exception {
        board.addFirst(Card.create("RS3"));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("R+4")));
        int score = board.applyLaydown(laydown);
        assertEquals(7, score);
    }

    @Test
    public void testScoring2Down() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("RS2")));
        laydown.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 2), Card.create("RS3")));
        int score = board.applyLaydown(laydown);
        assertEquals(6, score);
    }

    @Test
    public void testScoring2Right() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("RS2")));
        laydown.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE), Card.create("RS3")));
        int score = board.applyLaydown(laydown);
        assertEquals(6, score);
    }

    @Test
    public void testScoring2RightDown() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("RS2")));
        laydown.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 1), Card.create("RS3")));
        int score = board.applyLaydown(laydown);
        assertEquals(8, score);
    }

    @Test
    public void testScoring2Box() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown1 = new Laydown();
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("RS2")));
        board.applyLaydown(laydown1);
        System.out.println("Before:");
        board.print();
        Laydown laydown2 = new Laydown();
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 1), Card.create("RS3")));
        laydown2.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("RS4")));

        int score = board.applyLaydown(laydown2);
        System.out.println("After:");
        board.print();
        assertEquals(17, score);
    }

    @Test
    public void testScoring2Clamp() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown1 = new Laydown();
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("RS2")));
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE), Card.create("RS3")));
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE), Card.create("RS4")));
        board.applyLaydown(laydown1);
        System.out.println("Before:");
        board.print();
        Laydown laydown2 = new Laydown();
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 1), Card.create("BS4")));
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 2), Card.create("YS4")));
        board.applyLaydown(laydown2);

        System.out.println("After:");
        board.print();

        Laydown laydown3 = new Laydown();
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE + 2), Card.create("YS3")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 2), Card.create("YS2")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 2), Card.create("YS1")));
        board.applyLaydown(laydown3);

        System.out.println("After:");
        board.print();

        Laydown laydown4 = new Laydown();
        laydown4.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("GS1")));
        laydown4.addMove(new Move(new Location(Board.MIDDLE - 1, Board.MIDDLE + 1), Card.create("GS2")));
        int score = board.applyLaydown(laydown4);
        System.out.println("After:");
        board.print();
        assertEquals(6, score);
    }

    @Test
    public void testScoring2Clamp2() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown1 = new Laydown();
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("RS2")));
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE), Card.create("RS3")));
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE), Card.create("RS4")));
        board.applyLaydown(laydown1);
        System.out.println("Before:");
        board.print();
        Laydown laydown2 = new Laydown();
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 1), Card.create("BS4")));
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 2), Card.create("YS4")));
        board.applyLaydown(laydown2);

        System.out.println("After:");
        board.print();

        Laydown laydown3 = new Laydown();
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE + 2), Card.create("YS3")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 2), Card.create("YS2")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 2), Card.create("YS1")));
        board.applyLaydown(laydown3);

        System.out.println("After:");
        board.print();

        Laydown laydown4 = new Laydown();
        laydown4.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("GS1")));
        laydown4.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 1), Card.create("GS2")));
        int score = board.applyLaydown(laydown4);
        System.out.println("After:");
        board.print();
        assertEquals(12, score);
    }

    @Test
    public void testScoring3Right() throws Exception {
        board.addFirst(Card.create("RS1"));
        Laydown laydown = new Laydown();
        laydown.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("RS2")));
        laydown.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE), Card.create("RS3")));
        laydown.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE), Card.create("RS4")));
        int score = board.applyLaydown(laydown);
        assertEquals(20, score);
    }

    @Test
    public void testScoring4Box() throws Exception {
        board.addFirst(Card.create("RS4"));
        Laydown laydown1 = new Laydown();
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE), Card.create("GS4")));
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE), Card.create("BS4")));
        laydown1.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE), Card.create("YS4")));
        board.applyLaydown(laydown1);

        Laydown laydown2 = new Laydown();
        laydown2.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 1), Card.create("RO4")));
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 1), Card.create("GO4")));
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE + 1), Card.create("BO4")));
        laydown2.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 1), Card.create("YO4")));
        board.applyLaydown(laydown2);

        Laydown laydown3 = new Laydown();
        laydown3.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 2), Card.create("RT4")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 2), Card.create("GT4")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE + 2), Card.create("BT4")));
        laydown3.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 2), Card.create("YT4")));
        board.applyLaydown(laydown3);

        Laydown laydown4 = new Laydown();
        laydown4.addMove(new Move(new Location(Board.MIDDLE, Board.MIDDLE + 3), Card.create("R+4")));
        laydown4.addMove(new Move(new Location(Board.MIDDLE + 1, Board.MIDDLE + 3), Card.create("G+4")));
        laydown4.addMove(new Move(new Location(Board.MIDDLE + 2, Board.MIDDLE + 3), Card.create("B+4")));
        laydown4.addMove(new Move(new Location(Board.MIDDLE + 3, Board.MIDDLE + 3), Card.create("Y+4")));

        int score = board.applyLaydown(laydown4);
        board.print();
        assertEquals(2560, score);
    }

//    /**
//     * Test of applyLaydown method, of class Board.
//     */
//    @Test
//    public void testApplyLaydown() throws Exception {
//        System.out.println("applyLaydown");
//        Laydown laydown = null;
//        Board instance = new Board();
//        int expResult = 0;
//        int result = instance.applyLaydown(laydown);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of validLaydown method, of class Board.
//     */
//    @Test
//    public void testValidLaydown() throws Exception {
//        System.out.println("validLaydown");
//        Laydown laydown = null;
//        Board instance = new Board();
//        instance.validLaydown(laydown);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isTouchingBoard method, of class Board.
//     */
//    @Test
//    public void testIsTouchingBoard() {
//        System.out.println("isTouchingBoard");
//        Location location = null;
//        Board instance = new Board();
//        boolean expResult = false;
//        boolean result = instance.isTouchingBoard(location);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isOverlappingCard method, of class Board.
//     */
//    @Test
//    public void testIsOverlappingCard() {
//        System.out.println("isOverlappingCard");
//        Location location = null;
//        Board instance = new Board();
//        boolean expResult = false;
//        boolean result = instance.isOverlappingCard(location);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of validateLot method, of class Board.
//     */
//    @Test
//    public void testValidateLot() throws Exception {
//        System.out.println("validateLot");
//        List<Card> lot = null;
//        Board instance = new Board();
//        instance.validateLot(lot);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of print method, of class Board.
//     */
//    @Test
//    public void testPrint() {
//        System.out.println("print");
//        Board instance = new Board();
//        instance.print();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addFirst method, of class Board.
//     */
//    @Test
//    public void testAddFirst() {
//        System.out.println("addFirst");
//        Card first = null;
//        Board instance = new Board();
//        instance.addFirst(first);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}

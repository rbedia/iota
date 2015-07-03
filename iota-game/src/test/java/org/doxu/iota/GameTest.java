package org.doxu.iota;

import java.util.ArrayList;
import java.util.List;
import org.doxu.iota.board.Board;
import org.doxu.iota.player.RandomPlayer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    private List<Player> players;
    private RandomPlayer player1;
    private RandomPlayer player2;
    private Game game;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        player1 = new RandomPlayer();
        player2 = new RandomPlayer();
        players.add(player1);
        players.add(player2);

        game = new Game();
        game.init(players);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInit() {
        assertEquals(game.getBoard(), player1.getBoard());
        assertEquals(game.getDeck(), player1.getDeck());
        assertEquals(1, player1.getIndex());

        assertEquals(game.getBoard(), player2.getBoard());
        assertEquals(game.getDeck(), player2.getDeck());
        assertEquals(2, player2.getIndex());

        assertEquals(Deck.FULL_DECK, game.getDeck().count());
        assertEquals(2, game.getGameLog().getPlayers());
    }

    @Test
    public void testDeal_0args() {
        List<Card> p1cards = new ArrayList<>();
        p1cards.add(game.getDeck().getCards().get(0));
        p1cards.add(game.getDeck().getCards().get(2));
        p1cards.add(game.getDeck().getCards().get(4));
        p1cards.add(game.getDeck().getCards().get(6));

        List<Card> p2cards = new ArrayList<>();
        p2cards.add(game.getDeck().getCards().get(1));
        p2cards.add(game.getDeck().getCards().get(3));
        p2cards.add(game.getDeck().getCards().get(5));
        p2cards.add(game.getDeck().getCards().get(7));

        game.deal();

        assertEquals(Deck.FULL_DECK - 8, game.getDeck().count());
        assertEquals(Hand.MAX_HAND, player1.getHand().cardCount());
        assertTrue(player1.getHand().getCards().contains(p1cards.get(0)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(1)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(2)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(3)));

        assertEquals(Hand.MAX_HAND, player2.getHand().cardCount());
        assertTrue(player2.getHand().getCards().contains(p2cards.get(0)));
        assertTrue(player2.getHand().getCards().contains(p2cards.get(1)));
        assertTrue(player2.getHand().getCards().contains(p2cards.get(2)));
        assertTrue(player2.getHand().getCards().contains(p2cards.get(3)));
    }

    @Test
    public void testDeal_Player() {
        List<Card> p1cards = new ArrayList<>();
        p1cards.add(game.getDeck().getCards().get(0));
        p1cards.add(game.getDeck().getCards().get(1));
        p1cards.add(game.getDeck().getCards().get(2));
        p1cards.add(game.getDeck().getCards().get(3));

        game.deal(player1);

        assertEquals(Deck.FULL_DECK - 4, game.getDeck().count());
        assertEquals(Hand.MAX_HAND, player1.getHand().cardCount());
        assertTrue(player1.getHand().getCards().contains(p1cards.get(0)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(1)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(2)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(3)));
    }

    @Test
    public void testDeal_PlayerLowDeck() {
        // drain the deck so there are only two cards left
        for (int i = 0; i < Deck.FULL_DECK - 2; i++) {
            game.getDeck().draw();
        }

        List<Card> p1cards = new ArrayList<>();
        p1cards.add(game.getDeck().getCards().get(0));
        p1cards.add(game.getDeck().getCards().get(1));

        game.deal(player1);

        assertEquals(0, game.getDeck().count());
        assertEquals(2, player1.getHand().cardCount());
        assertTrue(player1.getHand().getCards().contains(p1cards.get(0)));
        assertTrue(player1.getHand().getCards().contains(p1cards.get(1)));
    }

    @Test
    public void testPlayStartingCard() {
        Card firstCard = game.getDeck().getCards().get(0);
        game.playStartingCard();
        assertEquals(Deck.FULL_DECK - 1, game.getDeck().count());
        Location location = game.getBoard().findCard(firstCard);
        Location middle = new Location(Board.MIDDLE, Board.MIDDLE);
        assertEquals(middle, location);
    }

    @Test
    public void testSetGameover() {
        game.setGameover(true);
        assertTrue(game.step());
    }
}

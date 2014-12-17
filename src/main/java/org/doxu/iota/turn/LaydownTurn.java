package org.doxu.iota.turn;

import org.doxu.iota.Hand;
import org.doxu.iota.IllegalLaydownException;
import org.doxu.iota.Laydown;
import org.doxu.iota.Player;

public class LaydownTurn extends BaseTurn {

    private final Laydown laydown;

    public LaydownTurn(Laydown laydown, Player player) {
        super(player);
        this.laydown = laydown;
    }

    @Override
    public void execute() {
        laydown(laydown, player);
    }

    private void laydown(Laydown laydown, Player player) {
        try {
            // TODO verify that all cards in laydown came from player's hand
            int score = game.getBoard().applyLaydown(laydown);
            // Double points for playing all cards in one turn
            if (laydown.getCards().size() == Hand.MAX_HAND) {
                score *= 2;
            }
            player.getHand().remove(laydown.getCards());
            game.deal(player);
            if (player.getHand().isEmpty()) {
                System.out.println("Player " + player.getDisplayName() + " went out. Score doubled.");
                score *= 2;
                game.setGameover(true);
            }
            player.addScore(score);
            game.resetPassCount();
        } catch (IllegalLaydownException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

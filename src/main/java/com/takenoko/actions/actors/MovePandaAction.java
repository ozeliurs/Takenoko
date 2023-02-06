package com.takenoko.actions.actors;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import java.util.Map;

/** This class represents the action of moving the panda. */
@ActionAnnotation(ActionType.DEFAULT)
public class MovePandaAction implements DefaultAction {
    private final PositionVector relativePositionVector;

    /**
     * Constructor for the MovePandaAction class.
     *
     * @param relativePositionVector the position vector to move the panda
     */
    public MovePandaAction(PositionVector relativePositionVector) {
        this.relativePositionVector = relativePositionVector;
    }

    public static boolean canBePlayed(Board board) {
        return !board.getTilesWithoutPond().isEmpty();
    }

    /**
     * Move the panda with a vector on the board and display a message.
     *
     * @param board the board
     * @param botManager the bot manager
     * @return the action result
     */
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        // move the panda
        Map<PositionVector, LayerBambooStack> bambooStack = board.movePanda(relativePositionVector);
        botManager.displayMessage(
                botManager.getName()
                        + " moved the panda with "
                        + relativePositionVector
                        + " to position "
                        + board.getPandaPosition());

        if (!bambooStack.isEmpty()) {
            // eat bamboo
            botManager
                    .getInventory()
                    .collectBamboo(board.getTileAt(board.getPandaPosition()).getColor());
            botManager.displayMessage(
                    "Panda ate one "
                            + board.getTileAt(board.getPandaPosition()).getColor()
                            + " bamboo for "
                            + botManager.getName());
        } else {
            // no bamboo to eat
            botManager.displayMessage("Panda didn't eat any bamboo");
        }
        return new ActionResult(1);
    }

    @Override
    public String toString() {
        return "MovePandaAction {" + relativePositionVector + "}";
    }
}

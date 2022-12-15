package com.takenoko.actors.panda;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.player.Action;
import com.takenoko.vector.PositionVector;

public class MovePandaAction implements Action {
    private final PositionVector positionVector;

    public MovePandaAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    @Override
    public void execute(Board board, BotManager botManager) {
        board.getActorsManager().getPanda().move(positionVector);
    }
}

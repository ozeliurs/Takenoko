package com.takenoko.bot.unitary;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.bot.utils.PandaPathfinding;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.vector.PositionVector;

public class SmartPanda extends PriorityBot {
    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // If the panda is not on the bamboo, move it to the bamboo
        PositionVector move = new PandaPathfinding(board, botState).getPosition();

        if (move != null) {
            this.addActionWithPriority(new MovePandaAction(move), DEFAULT_PRIORITY);
            this.addActionWithPriority(new ForcedMovePandaAction(move), DEFAULT_PRIORITY);
        }
    }
}

package com.takenoko.bot.unitary;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.bot.utils.PandaPathfinding;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

public class RushPandaBot extends PriorityBot {
    @Override
    public Action chooseAction(Board board, BotState botState, History history) {

        // If the panda is not on the bamboo, move it to the bamboo
        this.addActionWithPriority(
                new PandaPathfinding(board, botState).getMovePandaAction(), DEFAULT_PRIORITY);
        this.addActionWithPriority(
                new ForcedMovePandaAction(new PandaPathfinding(board, botState).getPosition()),
                DEFAULT_PRIORITY);

        return super.chooseAction(board, botState, history);
    }
}

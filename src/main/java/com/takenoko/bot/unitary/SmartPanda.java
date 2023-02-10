package com.takenoko.bot.unitary;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.bot.utils.pathfinding.panda.PandaPathfinding;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.objective.PandaObjective;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;

public class SmartPanda extends PriorityBot {
    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // If the panda is not on the bamboo, move it to the bamboo

        Map<PandaObjective, List<PositionVector>> completionMap =
                PandaPathfinding.getPandaMoves(board, botState);
        if (completionMap.size() != 0) {
            for (Entry<PandaObjective, List<PositionVector>> objective : completionMap.entrySet()) {
                for (PositionVector position : objective.getValue()) {
                    this.addActionWithPriority(
                            new MovePandaAction(position),
                            objective.getKey().getPoints()
                                    / objective.getKey().getCompletion(board, botState));
                    this.addActionWithPriority(
                            new ForcedMovePandaAction(position),
                            objective.getKey().getPoints()
                                    / objective.getKey().getCompletion(board, botState));
                }
            }
        }
    }
}

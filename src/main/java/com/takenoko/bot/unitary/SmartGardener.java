package com.takenoko.bot.unitary;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.bot.utils.pathfinding.gardener.GardenerPathfinding;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.objective.Objective;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;

public class SmartGardener extends PriorityBot {

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        Map<Objective, List<PositionVector>> completionMap =
                GardenerPathfinding.getGardenerMoves(board, botState);
        if (completionMap.size() > 0) {
            for (Entry<Objective, List<PositionVector>> objective : completionMap.entrySet()) {
                for (PositionVector position : objective.getValue()) {
                    this.addActionWithPriority(
                            new MoveGardenerAction(position),
                            objective.getKey().getPoints() / (double) objective.getValue().size());
                }
            }
        }
    }
}

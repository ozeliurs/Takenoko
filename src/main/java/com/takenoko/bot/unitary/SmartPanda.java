package com.takenoko.bot.unitary;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.bot.FullRandomBot;
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

    private FullRandomBot randomBot;

    public SmartPanda() {
        this.randomBot = new FullRandomBot();
    }

    public SmartPanda(FullRandomBot randomBot) {
        this.randomBot = randomBot;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // If the panda is not on the bamboo, move it to the bamboo

        Map<PandaObjective, List<PositionVector>> completionMap =
                PandaPathfinding.getPandaMoves(board, botState);
        if (completionMap.size() == 0) {
            this.addActionWithPriority(
                    this.randomBot.getRandomForcedMovePandaAction(board), DEFAULT_PRIORITY);
            this.addActionWithPriority(
                    this.randomBot.getRandomMovePandaAction(board), DEFAULT_PRIORITY);
        } else {

            for (Entry<PandaObjective, List<PositionVector>> objective : completionMap.entrySet()) {
                for (PositionVector position : objective.getValue()) {
                    this.addActionWithPriority(
                            new MovePandaAction(position),
                            objective.getKey().getPoints() - (double) objective.getValue().size());
                    this.addActionWithPriority(
                            new ForcedMovePandaAction(position),
                            objective.getKey().getPoints() - (double) objective.getValue().size());
                }
            }
        }
    }
}

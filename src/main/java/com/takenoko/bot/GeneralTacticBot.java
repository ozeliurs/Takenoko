package com.takenoko.bot;

import static com.takenoko.bot.utils.pathfinding.panda.PandaPathfinding.getPandaMovesThatEatBamboo;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.bot.unitary.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;

public class GeneralTacticBot extends PriorityBot {
    public GeneralTacticBot() {
        super();
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // We do not care if action is available or not, the unavailable actions will be removed by
        // the super method.

        this.addWithSquash(
                new IrrigationMaster(
                                Map.of(
                                        "placeIrrigationCoefficient", 9,
                                        "drawIrrigationCoefficient", 10,
                                        "storeIrrigationCoefficient", 11,
                                        "maxIrrigationToChooseToDraw", 4))
                        .compute(board, botState, history),
                20,
                25);
        this.addWithSquash(new SmartObjective().compute(board, botState, history), 10, 15);

        this.addWithSquash(new WeatherMaster().compute(board, botState, history), 5, 10);

        this.addWithSquash(new SmartPattern().compute(board, botState, history), 18, 25);

        this.addWithSquash(new SmartPanda().compute(board, botState, history), 0, 2);
        this.addWithSquash(new SmartGardener().compute(board, botState, history), 0, 2);

        this.addWithSquash(
                new SmartDrawImprovement(
                                Map.of(
                                        ImprovementType.WATERSHED, 3,
                                        ImprovementType.ENCLOSURE, 2))
                        .compute(board, botState, history),
                0,
                10);

        List<PositionVector> pandaMoveThatEatBamboo = getPandaMovesThatEatBamboo(board);

        if (!pandaMoveThatEatBamboo.isEmpty()) {
            this.addActionWithPriority(new MovePandaAction(pandaMoveThatEatBamboo.get(0)), -100);
            this.addActionWithPriority(
                    new ForcedMovePandaAction(pandaMoveThatEatBamboo.get(0)), -100);
        }
    }
}

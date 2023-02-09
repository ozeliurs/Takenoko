package com.takenoko.bot;

import com.takenoko.bot.unitary.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import java.util.Map;

public class GeneralTacticBot extends PriorityBot {
    public GeneralTacticBot() {
        super();
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // We do not care if action is available or not, the unavailable actions will be removed by
        // the super method.

        this.add(
                new IrrigationMaster(
                                Map.of(
                                        "placeIrrigationCoefficient", 10,
                                        "drawIrrigationCoefficient", 9,
                                        "storeIrrigationCoefficient", 8,
                                        "maxIrrigationToChooseToDraw", 5))
                        .compute(board, botState, history));

        this.add(new WeatherMaster().compute(board, botState, history));

        this.addWithOffset(new SmartPanda().compute(board, botState, history), 2);

        this.addWithOffset(new SmartGardener().compute(board, botState, history), 1);

        this.addWithOffset(new SmartPattern().compute(board, botState, history), 9);

        this.addWithOffset(new SmartObjective().compute(board, botState, history), 4);

        this.addWithOffset(
                new SmartDrawImprovement(
                                Map.of(
                                        ImprovementType.WATERSHED, 4,
                                        ImprovementType.ENCLOSURE, 1))
                        .compute(board, botState, history),
                0);
    }
}

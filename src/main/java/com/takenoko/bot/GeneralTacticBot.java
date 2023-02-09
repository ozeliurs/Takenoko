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
                                        "drawIrrigationCoefficient", 10,
                                        "storeIrrigationCoefficient", 11,
                                        "maxIrrigationToChooseToDraw", 4))
                        .compute(board, botState, history));

        this.add(new WeatherMaster().compute(board, botState, history));

        this.addWithOffset(new SmartPanda().compute(board, botState, history), 5);

        this.addWithOffset(new SmartGardener().compute(board, botState, history), 5);

        this.addWithOffset(new SmartPattern().compute(board, botState, history), 9);

        this.addWithOffset(new SmartObjective().compute(board, botState, history), 12);

        this.addWithOffset(
                new SmartDrawImprovement(
                                Map.of(
                                        ImprovementType.WATERSHED, 3,
                                        ImprovementType.ENCLOSURE, 2))
                        .compute(board, botState, history),
                0);
    }
}

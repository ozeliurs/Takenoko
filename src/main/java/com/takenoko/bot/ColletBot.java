package com.takenoko.bot;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.bot.unitary.SmartChooseAndApplyWeather;
import com.takenoko.bot.unitary.SmartDrawImprovement;
import com.takenoko.bot.unitary.SmartDrawIrrigation;
import com.takenoko.bot.unitary.SmartPanda;
import com.takenoko.bot.utils.GameProgress;
import com.takenoko.bot.utils.HistoryAnalysis;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.Cloudy;

import java.util.List;
import java.util.Map;

import static com.takenoko.bot.utils.pathfinding.panda.PandaPathfinding.getPandaMovesThatEatBamboo;

public class ColletBot extends PriorityBot {

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        addWithOffset(
                (new SmartDrawIrrigation(3)).compute(board, botState, history),
                100
        );
        addActionWithPriority(new DrawObjectiveAction(), 200);


        addWithOffset((new SmartPanda()).compute(board, botState, history), 50);
        List<PositionVector> pandaMoveThatEatBamboo = getPandaMovesThatEatBamboo(board);
        if (!pandaMoveThatEatBamboo.isEmpty()) {
            addActionWithPriority(new MovePandaAction(pandaMoveThatEatBamboo.get(0)), 25);
            addActionWithPriority(new ForcedMovePandaAction(pandaMoveThatEatBamboo.get(0)), 25);
        }


        if (HistoryAnalysis.getGameProgress(history).equals(GameProgress.EARLY_GAME)) {
            addWithOffset(
                    (new SmartChooseAndApplyWeather(new Cloudy())).compute(board, botState, history),
                    0);
            addWithOffset(
                    (new SmartDrawImprovement(
                            Map.of(
                                    ImprovementType.WATERSHED,
                                    1,
                                    ImprovementType.FERTILIZER,
                                    1)))
                            .compute(board, botState, history),
                    0);
        }

        addWithOffset((new SmartChooseAndApplyWeather()).compute(board, botState, history), 0);


    }
}

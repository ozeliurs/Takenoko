package com.takenoko.bot;

import static com.takenoko.bot.utils.pathfinding.panda.PandaPathfinding.getPandaMovesThatEatBamboo;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
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
import com.takenoko.objective.ObjectiveTypes;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.Cloudy;
import java.util.List;
import java.util.Map;

public class ColletBot extends PriorityBot {

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        addWithOffset((new SmartDrawIrrigation(3)).compute(board, botState, history), 100);

        if (board.hasObjectiveTypeInDeck(ObjectiveTypes.PANDA)) {
            addActionWithPriority(new DrawObjectiveAction(ObjectiveTypes.PANDA), 200);
        } else if (board.hasObjectiveTypeInDeck(ObjectiveTypes.GARDENER)) {
            addActionWithPriority(new DrawObjectiveAction(ObjectiveTypes.GARDENER), 200);
        } else {
            addActionWithPriority(new DrawObjectiveAction(ObjectiveTypes.SHAPE), 200);
        }

        addWithOffset((new SmartPanda()).compute(board, botState, history), 50);
        List<PositionVector> pandaMoveThatEatBamboo = getPandaMovesThatEatBamboo(board);
        if (!pandaMoveThatEatBamboo.isEmpty()) {
            addActionWithPriority(new MovePandaAction(pandaMoveThatEatBamboo.get(0)), 25);
            addActionWithPriority(new ForcedMovePandaAction(pandaMoveThatEatBamboo.get(0)), 25);
        }

        if (HistoryAnalysis.getGameProgress(history).equals(GameProgress.EARLY_GAME)) {
            addWithOffset(
                    (new SmartChooseAndApplyWeather(new Cloudy()))
                            .compute(board, botState, history),
                    50);
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
        if (HistoryAnalysis.analyzeRushPanda(history).keySet().stream()
                .anyMatch(k -> k != history.getCurrentBotManagerUUID())) {
            addWithOffset(
                    (new SmartDrawImprovement(Map.of(ImprovementType.ENCLOSURE, 3)))
                            .compute(board, botState, history),
                    0);
            if (botState.getInventory().hasImprovement(ImprovementType.ENCLOSURE)
                    && !board.getAvailableImprovementPositions().isEmpty()) {
                addActionWithPriority(
                        new ApplyImprovementFromInventoryAction(
                                ImprovementType.ENCLOSURE,
                                board.getAvailableImprovementPositions().get(0)),
                        0);
            }
        }

        addWithOffset((new SmartChooseAndApplyWeather()).compute(board, botState, history), 0);
    }
}

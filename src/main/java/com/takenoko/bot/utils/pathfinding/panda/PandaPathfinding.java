package com.takenoko.bot.utils.pathfinding.panda;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.objective.PandaObjective;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class PandaPathfinding {
    private PandaPathfinding() {}

    public static Map<PandaObjective, List<PositionVector>> getPandaMoves(
            Board board, BotState botState) {
        return botState.getNotAchievedObjectives().stream()
                .filter(PandaObjective.class::isInstance)
                .map(PandaObjective.class::cast)
                .map(
                        pandaObjective ->
                                Pair.of(
                                        pandaObjective,
                                        pandaObjective
                                                .getWhereToEatToComplete(board, botState)
                                                .stream()
                                                .filter(
                                                        positionToEat ->
                                                                board
                                                                        .getPandaPossibleMoves()
                                                                        .stream()
                                                                        // convert move position to
                                                                        // absolute position
                                                                        .map(
                                                                                pandaMove ->
                                                                                        board.getPandaPosition()
                                                                                                .add(
                                                                                                        pandaMove)
                                                                                                .toPositionVector())
                                                                        // check if the move is
                                                                        // possible
                                                                        .toList()
                                                                        .contains(positionToEat))
                                                .toList()))
                .filter(v -> !v.getValue().isEmpty())
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}

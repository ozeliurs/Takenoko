package com.takenoko.bot.utils.pathfinding.gardener;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.objective.MultipleGardenerObjective;
import com.takenoko.objective.Objective;
import com.takenoko.objective.SingleGardenerObjective;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public class GardenerPathfinding {

    private GardenerPathfinding() {}

    @NotNull
    public static Map<Objective, List<PositionVector>> getGardenerMoves(
            Board board, BotState botState) {
        return Stream.concat(
                        // SimpleGardenerObjective Moves
                        botState.getNotAchievedObjectives().stream()
                                .filter(v -> (v.getClass() == SingleGardenerObjective.class))
                                .map(
                                        v ->
                                                Pair.of(
                                                        v,
                                                        ((SingleGardenerObjective) v)
                                                                        .getPositionsToComplete(
                                                                                board)
                                                                        .stream()
                                                                        .filter(
                                                                                v1 ->
                                                                                        board.getGardenerPossibleMoves()
                                                                                                .contains(
                                                                                                        v1))
                                                                        .toList())),
                        // MultipleGardenerObjective Moves
                        botState.getNotAchievedObjectives().stream()
                                .filter(v -> (v.getClass() == MultipleGardenerObjective.class))
                                .map(
                                        v ->
                                                Pair.of(
                                                        v,
                                                        ((MultipleGardenerObjective) v)
                                                                        .getPositionsToComplete(
                                                                                board)
                                                                        .stream()
                                                                        .filter(
                                                                                v1 ->
                                                                                        board.getGardenerPossibleMoves()
                                                                                                .contains(
                                                                                                        v1))
                                                                        .toList())))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
}

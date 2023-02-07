package com.takenoko.bot.utils;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.objective.MultipleGardenerObjective;
import com.takenoko.objective.SingleGardenerObjective;
import com.takenoko.vector.PositionVector;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class GardenerPathfinding {
    private final Board board;
    private final BotState botState;

    public GardenerPathfinding(Board board, BotState botState) {
        this.board = board;
        this.botState = botState;
    }

    public PositionVector getPosition() {
        List<PositionVector> pandaMoves =
                Stream.of(
                                // SimpleGardenerObjective Moves
                                botState.getNotAchievedObjectives().stream()
                                        .filter(
                                                v ->
                                                        (v.getClass()
                                                                == SingleGardenerObjective.class))
                                        .map(
                                                v ->
                                                        ((SingleGardenerObjective) v)
                                                                .getPositionsToComplete(board))
                                        .flatMap(List::stream)
                                        .filter(v -> board.getGardenerPossibleMoves().contains(v))
                                        .toList(),
                                // MultipleGardenerObjective Moves
                                botState.getNotAchievedObjectives().stream()
                                        .filter(
                                                v ->
                                                        (v.getClass()
                                                                == MultipleGardenerObjective.class))
                                        .map(
                                                v ->
                                                        ((MultipleGardenerObjective) v)
                                                                .getPositionsToComplete(board))
                                        .flatMap(List::stream)
                                        .filter(v -> board.getGardenerPossibleMoves().contains(v))
                                        .toList())
                        .flatMap(Collection::stream)
                        .toList();

        if (pandaMoves.isEmpty()) {
            return null;
        }

        return pandaMoves.stream().toList().get(0);
    }

    public Action getMoveGardenerAction() {
        if (getPosition() == null) {
            return null;
        }
        return new MoveGardenerAction(getPosition());
    }
}

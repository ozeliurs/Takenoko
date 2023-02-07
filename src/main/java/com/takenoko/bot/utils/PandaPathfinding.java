package com.takenoko.bot.utils;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.objective.PandaObjective;
import com.takenoko.vector.PositionVector;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PandaPathfinding {
    private final Board board;
    private final BotState botState;

    public PandaPathfinding(Board board, BotState botState) {
        this.board = board;
        this.botState = botState;
    }

    public PositionVector getPosition() {
        HashSet<PositionVector> positionsToEat =
                botState.getNotAchievedObjectives().stream()
                        .filter(PandaObjective.class::isInstance)
                        .map(PandaObjective.class::cast)
                        .map(v -> v.getWhereToEatToComplete(board, botState))
                        .flatMap(List::stream)
                        .collect(Collectors.toCollection(HashSet::new));

        positionsToEat.retainAll(
                board.getPandaPossibleMoves().stream()
                        .map(v -> board.getPandaPosition().add(v).toPositionVector())
                        .collect(Collectors.toCollection(HashSet::new)));

        if (!positionsToEat.isEmpty()) {
            return positionsToEat.stream()
                    .toList()
                    .get(0)
                    .sub(board.getPandaPosition().toPositionVector())
                    .toPositionVector();
        }
        return null;
    }

    public Action getMovePandaAction() {
        PositionVector position = getPosition();
        if (position == null) {
            return null;
        }
        return new MovePandaAction(position);
    }

    public Action getForcedMovePandaAction() {
        PositionVector position = getPosition();
        if (position == null) {
            return null;
        }
        return new ForcedMovePandaAction(position);
    }
}

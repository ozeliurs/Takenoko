package com.takenoko.bot;

import com.takenoko.actions.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FullRandomBot implements Bot {
    SecureRandom random;

    public FullRandomBot() {
        random = new SecureRandom();
    }

    @Override
    public Action chooseAction(Board board, BotState botState) {
        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            return new ChooseIfApplyWeatherAction(random.nextBoolean());
        }

        List<Action> actions = new ArrayList<>();

        for (Class<? extends Action> actionClass : botState.getAvailableActions()) {
            if (actionClass.equals(MoveGardenerAction.class)) {
                actions.add(getRandomMoveGardenerAction(board));
            } else if (actionClass.equals(MovePandaAction.class)) {
                actions.add(getRandomMovePandaAction(board));
            } else if (actionClass.equals(PlaceTileAction.class)) {
                actions.add(getRandomPlaceTileAction(board));
            }
        }

        actions.removeIf(Objects::isNull);
        return actions.get(random.nextInt(actions.size()));
    }

    private Action getRandomPlaceTileAction(Board board) {
        List<Tile> availableTiles = board.getAvailableTiles();
        List<PositionVector> availableTilePositions = board.getAvailableTilePositions();
        if (availableTiles.isEmpty() || availableTilePositions.isEmpty()) {
            return null;
        }
        return new PlaceTileAction(
                availableTiles.get(random.nextInt(availableTiles.size())),
                availableTilePositions.get(random.nextInt(availableTilePositions.size())));
    }

    private Action getRandomMovePandaAction(Board board) {
        List<PositionVector> pandaPossibleMoves = board.getPandaPossibleMoves();
        if (pandaPossibleMoves.isEmpty()) {
            return null;
        }
        return new MovePandaAction(
                pandaPossibleMoves.get(random.nextInt(pandaPossibleMoves.size())));
    }

    private Action getRandomMoveGardenerAction(Board board) {
        List<PositionVector> gardenerPossibleMoves = board.getGardenerPossibleMoves();
        if (gardenerPossibleMoves.isEmpty()) {
            return null;
        }
        return new MoveGardenerAction(
                gardenerPossibleMoves.get(random.nextInt(gardenerPossibleMoves.size())));
    }
}

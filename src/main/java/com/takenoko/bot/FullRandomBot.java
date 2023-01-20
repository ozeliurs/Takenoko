package com.takenoko.bot;

import com.takenoko.actions.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.ImprovementType;
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
        List<Action> actions = new ArrayList<>();

        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            actions.add(new ChooseIfApplyWeatherAction(random.nextBoolean()));
        }

        if (botState.getAvailableActions().contains(DrawTileAction.class)) {
            actions.add(new DrawTileAction());
        }

        if (botState.getAvailableActions().contains(PlaceTileAction.class)) {
            actions.add(getRandomPlaceTileAction(board));
        }

        if (botState.getAvailableActions().contains(MoveGardenerAction.class)) {
            actions.add(getRandomMoveGardenerAction(board));
        }

        if (botState.getAvailableActions().contains(MovePandaAction.class)) {
            actions.add(getRandomMovePandaAction(board));
        }

        if (botState.getAvailableActions().contains(ApplyImprovementAction.class)) {
            actions.add(getRandomApplyImprovementAction(board));
        }

        if (botState.getAvailableActions().contains(StoreImprovementAction.class)) {
            actions.add(
                    new StoreImprovementAction(
                            ImprovementType.values()[
                                    random.nextInt(ImprovementType.values().length)]));
        }

        actions.removeIf(Objects::isNull);
        return actions.get(random.nextInt(actions.size()));
    }

    private Action getRandomApplyImprovementAction(Board board) {
        List<PositionVector> positions = board.getAvailableImprovementPositions();

        List<ImprovementType> imp = new ArrayList<>();
        if (board.hasImprovementInDeck(ImprovementType.FERTILIZER)) {
            imp.add(ImprovementType.FERTILIZER);
        }
        if (board.hasImprovementInDeck(ImprovementType.ENCLOSURE)) {
            imp.add(ImprovementType.ENCLOSURE);
        }

        return new ApplyImprovementAction(
                imp.get(random.nextInt(imp.size())),
                positions.get(random.nextInt(positions.size())));
    }

    private Action getRandomPlaceTileAction(Board board) {
        List<Tile> availableTiles = board.peekTileDeck();
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

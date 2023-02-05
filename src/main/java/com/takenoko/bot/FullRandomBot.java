package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.improvement.ApplyImprovementAction;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
import com.takenoko.actions.improvement.DrawImprovementAction;
import com.takenoko.actions.improvement.StoreImprovementAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.Weather;
import com.takenoko.weather.WeatherFactory;
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
            return new ChooseIfApplyWeatherAction(random.nextBoolean());
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
            actions.add(new StoreImprovementAction());
        }

        if (botState.getAvailableActions().contains(DrawImprovementAction.class)) {
            actions.add(getRandomDrawAction(board));
        }

        if (botState.getAvailableActions().contains(ApplyImprovementFromInventoryAction.class)) {
            actions.add(getRandomApplyImprovementFromInventoryAction(board, botState));
        }

        if (botState.getAvailableActions().contains(ChooseAndApplyWeatherAction.class)) {
            actions.add(getRandomChooseAndApplyWeatherAction());
        }
        if (botState.getAvailableActions().contains(DrawObjectiveAction.class)) {
            actions.add(new DrawObjectiveAction());
        }
        if (botState.getAvailableActions().contains(RedeemObjectiveAction.class)) {
            actions.add(getRandomRedeemObjectiveAction(botState));
        }
        if (botState.getAvailableActions().contains(DrawIrrigationAction.class)) {
            actions.add(new DrawIrrigationAction());
        }
        if (botState.getAvailableActions().contains(PlaceIrrigationAction.class)) {
            actions.add(getRandomPlaceIrrigationAction(board));
        }
        if (botState.getAvailableActions().contains(PlaceIrrigationFromInventoryAction.class)) {
            actions.add(getRandomPlaceIrrigationFromInventoryAction(board));
        }
        if (botState.getAvailableActions().contains(StoreIrrigationInInventoryAction.class)) {
            actions.add(new StoreIrrigationInInventoryAction());
        }
        if (botState.getAvailableActions().contains(ForcedMovePandaAction.class)) {
            actions.add(getRandomForcedMovePandaAction(board));
        }

        actions.removeIf(Objects::isNull);

        if (actions.isEmpty())
            throw new IllegalStateException(
                    "FullRandomBot didn't find any action ("
                            + botState.getAvailableActions()
                            + ")");

        return actions.get(random.nextInt(actions.size()));
    }

    private Action getRandomForcedMovePandaAction(Board board) {
        List<PositionVector> pandaPossibleMoves = board.getPandaPossibleMoves();
        if (pandaPossibleMoves.isEmpty()) {
            return null;
        }
        return new ForcedMovePandaAction(
                pandaPossibleMoves.get(random.nextInt(pandaPossibleMoves.size())));
    }

    private Action getRandomApplyImprovementFromInventoryAction(Board board, BotState botState) {
        List<ImprovementType> availableImprovements =
                botState.getInventory().getInventoryImprovements();
        List<PositionVector> availablePositions = board.getAvailableImprovementPositions();
        if (availableImprovements.isEmpty()) {
            return null;
        }
        return new ApplyImprovementFromInventoryAction(
                availableImprovements.get(random.nextInt(availableImprovements.size())),
                availablePositions.get(random.nextInt(availablePositions.size())));
    }

    private Action getRandomPlaceIrrigationAction(Board board) {
        List<EdgePosition> positions = board.getAvailableIrrigationPositions();

        if (positions.isEmpty()) return null;

        return new PlaceIrrigationAction(positions.get(random.nextInt(positions.size())));
    }

    private Action getRandomPlaceIrrigationFromInventoryAction(Board board) {
        List<EdgePosition> availableIrrigationPositions = board.getAvailableIrrigationPositions();
        if (availableIrrigationPositions.isEmpty()) {
            return null;
        }
        return new PlaceIrrigationFromInventoryAction(
                availableIrrigationPositions.get(
                        random.nextInt(availableIrrigationPositions.size())));
    }

    private Action getRandomRedeemObjectiveAction(BotState botState) {
        return new RedeemObjectiveAction(
                botState.getAchievedObjectives()
                        .get(random.nextInt(botState.getAchievedObjectives().size())));
    }

    private Action getRandomChooseAndApplyWeatherAction() {
        Weather weather =
                WeatherFactory.values()[random.nextInt(WeatherFactory.values().length)]
                        .createWeather();
        return new ChooseAndApplyWeatherAction(weather);
    }

    private Action getRandomDrawAction(Board board) {
        List<ImprovementType> improvements = new ArrayList<>();

        if (board.hasImprovementInDeck(ImprovementType.ENCLOSURE)) {
            improvements.add(ImprovementType.ENCLOSURE);
        }

        if (board.hasImprovementInDeck(ImprovementType.FERTILIZER)) {
            improvements.add(ImprovementType.FERTILIZER);
        }
        if (improvements.isEmpty()) return new DrawImprovementAction(ImprovementType.FERTILIZER);

        return new DrawImprovementAction(improvements.get(random.nextInt(improvements.size())));
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

        if (positions.isEmpty() || imp.isEmpty()) return null;

        return new ApplyImprovementAction(positions.get(random.nextInt(positions.size())));
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

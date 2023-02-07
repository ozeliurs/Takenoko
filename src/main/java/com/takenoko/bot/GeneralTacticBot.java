package com.takenoko.bot;

import static com.takenoko.bot.irrigation.pathfinding.IrrigationPathFinding.getShortestIrrigationPath;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.tile.PlaceTileWithImprovementAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.bot.utils.GardenerPathfinding;
import com.takenoko.bot.utils.HistoryAnalysis;
import com.takenoko.bot.utils.PandaPathfinding;
import com.takenoko.engine.*;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.EmperorObjective;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PandaObjective;
import com.takenoko.objective.PatternObjective;
import com.takenoko.shape.Shape;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.WeatherFactory;
import java.util.*;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class GeneralTacticBot extends PriorityBot {
    private static final Integer ARBITRARY_MARGIN = 0;

    private final List<EdgePosition> irrigationToPlace = new ArrayList<>();
    private final Map<Class<? extends Action>, Integer> priorityByActionClass;
    private static final int ARBITRARY_MAX_IRRIGATION_TO_CHOOSE_TO_DRAW = 3;

    public GeneralTacticBot(Map<Class<? extends Action>, Integer> priorityByActionClass) {
        super();
        this.priorityByActionClass = priorityByActionClass;
    }

    @SuppressWarnings({"java:S3599", "java:S1171"})
    public GeneralTacticBot() {
        this(
                new HashMap<>() {
                    {
                        put(PlaceIrrigationFromInventoryAction.class, 10);
                        put(PlaceTileAction.class, 9);
                        put(PlaceTileWithImprovementAction.class, 9);
                        put(ChooseIfApplyWeatherAction.class, 8);
                        put(ChooseAndApplyWeatherAction.class, 7);
                        put(StoreIrrigationInInventoryAction.class, 5);
                        put(RedeemObjectiveAction.class, 4);
                        put(DrawIrrigationAction.class, 3);
                        put(MovePandaAction.class, 2);
                        put(ForcedMovePandaAction.class, 2);
                        put(MoveGardenerAction.class, 1);
                    }
                });
    }

    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        // We do not care if action is available or not, the unavailable actions will be removed by
        // the super method.

        // Complete a PatternObjective by adding irrigation channels
        if (botState.getAvailableActions().contains(PlaceIrrigationFromInventoryAction.class)) {
            // Because it is empty, we have to analyze the board to find the best irrigation path
            if (irrigationToPlace.isEmpty()) {
                analyzeIrrigationToPlaceToCompletePatternObjective(board, botState);
            }
            // If there is a path to complete the objective, we place the first edge
            if (!irrigationToPlace.isEmpty()) {
                this.addActionIfNotNull(
                        new PlaceIrrigationFromInventoryAction(irrigationToPlace.remove(0)),
                        priorityByActionClass.get(PlaceIrrigationFromInventoryAction.class));
            }
        }

        // Complete the shape of the current PatternObjective
        if (botState.getAvailableActions().contains(PlaceTileAction.class)
                || botState.getAvailableActions().contains(PlaceTileWithImprovementAction.class)) {
            Pair<PositionVector, Tile> tileToPlaceWithPosition =
                    analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective(board, botState);
            if (tileToPlaceWithPosition != null) {
                // We add both types of action to the bot. We consider that the wrong one will be
                // sorted by the priority bot
                this.addActionIfNotNull(
                        new PlaceTileAction(
                                tileToPlaceWithPosition.getRight(),
                                tileToPlaceWithPosition.getLeft()),
                        priorityByActionClass.get(PlaceTileAction.class));

                // Verify that the tile to place does not already have an improvement
                if (tileToPlaceWithPosition.getRight().getImprovement().isEmpty()) {
                    // We force the bot to place the watershed if he has one
                    if (botState.getInventory().hasImprovement(ImprovementType.WATERSHED)) {
                        this.addActionIfNotNull(
                                new PlaceTileWithImprovementAction(
                                        tileToPlaceWithPosition.getRight(),
                                        tileToPlaceWithPosition.getLeft(),
                                        ImprovementType.WATERSHED),
                                priorityByActionClass.get(PlaceTileWithImprovementAction.class));
                    } else if (botState.getInventory().hasImprovement()) {
                        // Otherwise place a """random""" one
                        this.addActionIfNotNull(
                                new PlaceTileWithImprovementAction(
                                        tileToPlaceWithPosition.getRight(),
                                        tileToPlaceWithPosition.getLeft(),
                                        botState.getInventory().getInventoryImprovements().get(0)),
                                priorityByActionClass.get(PlaceTileWithImprovementAction.class));
                    }
                }
            }
        }

        // Always apply the weather
        this.addActionIfNotNull(
                new ChooseIfApplyWeatherAction(true),
                priorityByActionClass.get(ChooseIfApplyWeatherAction.class));

        // Priority 7 : Apply sunny weather
        this.addActionIfNotNull(
                new ChooseAndApplyWeatherAction(WeatherFactory.SUNNY.createWeather()),
                priorityByActionClass.get(ChooseAndApplyWeatherAction.class));

        // Priority 6 : Apply windy weather
        // TODO

        // Priority 5 : Store irrigation channel
        this.addActionIfNotNull(
                new StoreIrrigationInInventoryAction(),
                priorityByActionClass.get(StoreIrrigationInInventoryAction.class));

        // Priority 4 : Redeem a PandaObjective if it is the last one in his hand, except if that
        // makes it win
        this.addActionIfNotNull(
                analyzeObjectivesToRedeem(botState, history),
                priorityByActionClass.get(RedeemObjectiveAction.class));

        // Priority 3 : draw an Irrigation
        if (botState.getInventory().getIrrigationChannelsCount()
                < ARBITRARY_MAX_IRRIGATION_TO_CHOOSE_TO_DRAW) {
            this.addActionIfNotNull(
                    new DrawIrrigationAction(),
                    priorityByActionClass.get(DrawIrrigationAction.class));
        }

        // Priority 2 : move the Panda
        this.addActionIfNotNull(
                new PandaPathfinding(board, botState).getMovePandaAction(),
                priorityByActionClass.get(MovePandaAction.class));
        this.addActionIfNotNull(
                new PandaPathfinding(board, botState).getForcedMovePandaAction(),
                priorityByActionClass.get(ForcedMovePandaAction.class));

        // Priority 1 : move the gardener
        this.addActionIfNotNull(
                new GardenerPathfinding(board, botState).getMoveGardenerAction(),
                priorityByActionClass.get(MoveGardenerAction.class));

        // Call the super method to get the action, he'll maybe call FullRandomBot.chooseAction() to
        // the rescue.
        return super.chooseAction(board, botState, history);
    }

    public RedeemObjectiveAction analyzeObjectivesToRedeem(BotState botState, History history) {
        /*
         * If we have only one panda objective, do not redeem it
         * If we have any other objective, redeem any of them
         */
        List<Objective> pandaObjectives =
                botState.getAchievedObjectives().stream()
                        .filter(PandaObjective.class::isInstance)
                        .toList();

        if (pandaObjectives.size() > 1) {
            return new RedeemObjectiveAction(pandaObjectives.get(0));
        }

        if (botState.getRedeemedObjectives().size() + 1
                != GameEngine.DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN.get(
                        history.getBotManagerUUIDs().size() + 1)) {
            return null;
        }

        if (pandaObjectives.size() == 1
                && pandaObjectives.get(0).getPoints()
                                + botState.getObjectiveScore()
                                + EmperorObjective.EMPEROR_BONUS
                        > new HistoryAnalysis(history).getMaxBotScore() + ARBITRARY_MARGIN) {
            return new RedeemObjectiveAction(pandaObjectives.get(0));
        }

        return null;
    }

    public List<PatternObjective> getCurrentPatternObjectives(BotState botState) {
        return botState.getObjectives().stream()
                .filter(PatternObjective.class::isInstance)
                .map(PatternObjective.class::cast)
                .toList();
    }

    public Pair<PositionVector, Tile> analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective(
            Board board, BotState botState) {
        // Get all the pattern objectives of the bot
        Stream<PatternObjective> patternObjectives =
                getCurrentPatternObjectives(botState).stream()
                        .filter(patternObjective -> !patternObjective.isAchieved())
                        .sorted(Comparator.comparing(Objective::getPoints).reversed());

        // Get all the shapes that could be completed by placing a tile
        List<Shape> uncompletedSubsetOfShapes =
                patternObjectives
                        .map(
                                patternObjective ->
                                        patternObjective
                                                .getShapeToCompletePatternObjective(board)
                                                .stream()
                                                .min(
                                                        Comparator.comparingInt(
                                                                v -> v.getElements().size())))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList();

        // Get the available tiles to place
        List<Tile> deckAvailableTiles = board.peekTileDeck();
        List<TileColor> deckAvailableColors =
                deckAvailableTiles.stream().map(Tile::getColor).toList();

        Optional<Shape> bestSubsetThanCanBeFurtherCompleted =
                uncompletedSubsetOfShapes.stream()
                        .filter(
                                shape ->
                                        shape.getElements().values().stream()
                                                .anyMatch(
                                                        tile ->
                                                                deckAvailableColors.contains(
                                                                        tile.getColor())))
                        .findFirst();

        if (bestSubsetThanCanBeFurtherCompleted.isPresent()) {
            Shape bestSubset = bestSubsetThanCanBeFurtherCompleted.get();
            return bestSubset.getElements().entrySet().stream()
                    .map(
                            entrySet ->
                                    Pair.of(
                                            entrySet.getKey(),
                                            board.peekTileDeck().stream()
                                                    .filter(
                                                            tile2 ->
                                                                    entrySet.getValue()
                                                                            .getColor()
                                                                            .equals(
                                                                                    tile2
                                                                                            .getColor()))
                                                    .findFirst()))
                    .filter(pair -> pair.getRight().isPresent())
                    .map(pair -> Pair.of(pair.getLeft(), pair.getRight().get()))
                    .findFirst()
                    .orElseThrow();
        }
        return null;
    }

    /**
     * This method will add the edge position to the irrigationToPlace list so that the bot knows
     * that he has to place them in order to complete is pattern objectiv
     *
     * @param board the board
     * @param botState the bot state
     */
    public void analyzeIrrigationToPlaceToCompletePatternObjective(Board board, BotState botState) {
        // Verify that the bot has
        // - at least one pattern objective that is not already completed
        if (!allPatternObjectivesAreCompleted(botState)) {
            // Get all the shapes that could be completed by placing an irrigation
            List<List<Shape>> candidateShapes = getCandidateShapes(board, botState);

            // If it is not empty, be intelligent
            if (!candidateShapes.isEmpty()) {
                List<List<EdgePosition>> allPossiblePaths = new ArrayList<>();
                for (List<Shape> shapeList : candidateShapes) {
                    for (Shape shape : shapeList) {
                        // Get the position vector and tile of the shape
                        Map<PositionVector, Tile> map = shape.getElements();
                        // Find the tiles that are not irrigated yet
                        List<PositionVector> candidateTilesToIrrigate =
                                map.keySet().stream()
                                        .filter(tile -> !board.isIrrigatedAt(tile))
                                        .toList();
                        // Find which irrigation to place to irrigate all the tiles of the shape
                        List<EdgePosition> possibleEdgePositions =
                                getShortestIrrigationPath(candidateTilesToIrrigate, board);

                        // Verify that the number of irrigation needed does not exceed the number of
                        // irrigation available
                        if (botState.getInventory().getIrrigationChannelsCount()
                                > possibleEdgePositions.size()) {
                            allPossiblePaths.add(possibleEdgePositions);
                        }
                    }
                }

                irrigationToPlace.clear();
                // Choose the shortest path
                allPossiblePaths.stream()
                        .min(Comparator.comparingInt(List::size))
                        .ifPresent(irrigationToPlace::addAll);
            }
        }
    }

    /**
     * Check if any of the pattern objectives are completed
     *
     * @param botState the bot state
     * @return true if there is at least a pattern objective completed, false otherwise
     */
    public boolean allPatternObjectivesAreCompleted(BotState botState) {
        for (Objective objective : getCurrentPatternObjectives(botState)) {
            if (!objective.isAchieved()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get all the shape matching the patterns on the current pattern objectives
     *
     * @param board the board
     * @param botState the bot state
     * @return the list of shapes matching the pattern objectives
     */
    public List<List<Shape>> getCandidateShapes(Board board, BotState botState) {
        List<PatternObjective> patternObjectives = getCurrentPatternObjectives(botState);

        List<List<Shape>> matchedPatterns = new ArrayList<>();
        for (PatternObjective patternObjective : patternObjectives) {
            matchedPatterns.add(patternObjective.getPattern().match(board, true));
        }

        return matchedPatterns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GeneralTacticBot that = (GeneralTacticBot) o;

        if (irrigationToPlace != null
                ? !irrigationToPlace.equals(that.irrigationToPlace)
                : that.irrigationToPlace != null) return false;
        return Objects.equals(priorityByActionClass, that.priorityByActionClass);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (irrigationToPlace != null ? irrigationToPlace.hashCode() : 0);
        result =
                31 * result
                        + (priorityByActionClass != null ? priorityByActionClass.hashCode() : 0);
        return result;
    }
}

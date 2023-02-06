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
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.engine.*;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.layers.tile.Tile;
import com.takenoko.objective.*;
import com.takenoko.shape.Shape;
import com.takenoko.ui.ConsoleUserInterface;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.WeatherFactory;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class GeneralTacticBot implements Bot {

    private final FullRandomBot fullRandomBot = new FullRandomBot();
    private final List<EdgePosition> irrigationToPlace = new ArrayList<>();
    private static final int ARBITRARY_MARGIN = 0;
    private static final ConsoleUserInterface console = new ConsoleUserInterface();

    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        /*
         * Intelligence to complete a PatternObjective by adding irrigation channels
         */
        if (botState.getAvailableActions().contains(PlaceIrrigationFromInventoryAction.class)) {
            console.displayDebug("BIG BRAIN MODE - PlaceIrrigationFromInventoryAction");
            // Because it is empty, we have to analyze the board to find the best irrigation path
            if (irrigationToPlace.isEmpty()) {
                console.displayDebug("BIG BRAIN MODE - analyzed irrigation");
                analyzeIrrigationToPlaceToCompletePatternObjective(board, botState);
            }
            // If there is a path to complete the objective, we place the first edge
            if (!irrigationToPlace.isEmpty()) {
                console.displayDebug("BIG BRAIN MODE - chose irrigationToPlace");
                return new PlaceIrrigationFromInventoryAction(irrigationToPlace.remove(0));
            }
        }

        /*
         * Intelligence to complete the shape of the current PatternObjective
         */
        if (botState.getAvailableActions().contains(PlaceTileAction.class)
                || botState.getAvailableActions().contains(PlaceTileWithImprovementAction.class)) {
            console.displayDebug("BIG BRAIN MODE - PlaceTileAction");
            // We analyze the board to find the best tile to place
            Pair<Tile, PositionVector> tileWithPosition =
                    analyzeTileToPlaceToCompleteShapeOfPatternObjective(board, botState);
            // If there is a tile to place, we place it
            if (tileWithPosition != null) {
                console.displayDebug("BIG BRAIN MODE - chose to place a tile");
                return new PlaceTileAction(tileWithPosition.getLeft(), tileWithPosition.getRight());
            }
        }

        /*
         * Always apply the weather
         */
        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            console.displayDebug("BIG BRAIN MODE - ChooseIfApplyWeatherAction");
            return new ChooseIfApplyWeatherAction(true);
        }

        /*
         * Intelligence when having the choice of the meteo to either :
         * - Choose the sun because it is the best move
         * - Choose the wind because it allows the bot to win next move <- WIP
         */
        if (botState.getAvailableActions().contains(ChooseAndApplyWeatherAction.class)) {
            console.displayDebug("BIG BRAIN MODE - ChooseAndApplyWeatherAction");
            return new ChooseAndApplyWeatherAction(WeatherFactory.SUNNY.createWeather());
        }

        /*
         * Always store the irrigation channel
         */
        if (botState.getAvailableActions().contains(StoreIrrigationInInventoryAction.class)) {
            console.displayDebug("BIG BRAIN MODE - StoreIrrigationInInventoryAction");
            return new StoreIrrigationInInventoryAction();
        }

        /*
         * Intelligence to not redeem a PandaObjective if it is the last one in his hand, except if that makes it win
         */
        if (botState.getAvailableActions().contains(RedeemObjectiveAction.class)) {
            console.displayDebug("BIG BRAIN MODE - RedeemObjectiveAction");
            RedeemObjectiveAction result = analyzeObjectivesToRedeem(botState, history);
            if (result != null) {
                console.displayDebug("BIG BRAIN MODE - chose to redeem an objective");
                return result;
            }
        }

        /*
         * Always draw an irrigation if nothing else worked
         */
        if (botState.getAvailableActions().contains(DrawIrrigationAction.class)
                && botState.getInventory().getIrrigationChannelsCount() < 3) {
            console.displayDebug("BIG BRAIN MODE - DrawIrrigationAction");
            return new DrawIrrigationAction();
        }

        if (botState.getAvailableActions().contains(MovePandaAction.class)
                || botState.getAvailableActions().contains(ForcedMovePandaAction.class)) {
            PositionVector pandaObjective = analyzePandaObjective(board, botState);
            if (pandaObjective != null) {
                console.displayDebug("BIG BRAIN MODE - chose to move the panda");
                if (botState.getAvailableActions().contains(MovePandaAction.class)) {
                    return new MovePandaAction(pandaObjective);
                } else {
                    return new ForcedMovePandaAction(pandaObjective);
                }
            }
        }

        if (botState.getAvailableActions().contains(MoveGardenerAction.class)) {
            Action action = analyzeGardenerAction(board, botState);
            if (action != null) {
                console.displayDebug("BIG BRAIN MODE - move to complete GardenerObjective");
                return action;
            }
        }

        console.displayMessage("NOT SO BIG BRAIN MODE - LET THE FULL RANDOM BOT DO THE JOB");
        return fullRandomBot.chooseAction(board, botState, history);
    }

    private PositionVector analyzePandaObjective(Board board, BotState botState) {
        HashSet<PositionVector> positionsToEat =
                botState.getNotAchievedObjectives().stream()
                        .filter(PandaObjective.class::isInstance)
                        .map(PandaObjective.class::cast)
                        .map(v -> v.getWhereToEatToComplete(board, botState))
                        .flatMap(List::stream)
                        .collect(Collectors.toCollection(HashSet::new));
        console.displayDebug("BIG BRAIN MODE - positionsToEat : " + positionsToEat);
        positionsToEat.retainAll(
                board.getPandaPossibleMoves().stream()
                        .map(v -> board.getPandaPosition().add(v).toPositionVector())
                        .collect(Collectors.toCollection(HashSet::new)));
        console.displayDebug(
                "BIG BRAIN MODE - positionsToEat after intersection : " + positionsToEat);
        if (!positionsToEat.isEmpty()) {
            PositionVector relativePosition =
                    positionsToEat.stream()
                            .toList()
                            .get(0)
                            .sub(board.getPandaPosition().toPositionVector())
                            .toPositionVector();
            console.displayDebug("BIG BRAIN MODE - chose to eat a bamboo with " + relativePosition);
            return relativePosition;
        }
        return null;
    }

    private Action analyzeGardenerAction(Board board, BotState botState) {

        List<Action> actionsToDo =
                Stream.of(
                                botState.getNotAchievedObjectives().stream()
                                        .filter(
                                                v ->
                                                        (v.getClass()
                                                                == SingleGardenerObjective.class))
                                        .map(
                                                v ->
                                                        ((SingleGardenerObjective) v)
                                                                .getActionsToComplete(board))
                                        .flatMap(List::stream)
                                        .filter(
                                                v ->
                                                        botState.getAvailableActions()
                                                                .contains(v.getClass()))
                                        .toList(),
                                botState.getNotAchievedObjectives().stream()
                                        .filter(
                                                v ->
                                                        (v.getClass()
                                                                == MultipleGardenerObjective.class))
                                        .map(
                                                v ->
                                                        ((MultipleGardenerObjective) v)
                                                                .getActionsToComplete(board))
                                        .flatMap(List::stream)
                                        .filter(
                                                v ->
                                                        botState.getAvailableActions()
                                                                .contains(v.getClass()))
                                        .toList())
                        .flatMap(Collection::stream)
                        .toList();

        if (actionsToDo.isEmpty()) {
            console.displayDebug("BIG BRAIN MODE - No gardener action to do");
            return null;
        }

        console.displayDebug(
                "BIG BRAIN MODE - Found optimal move {"
                        + actionsToDo.stream().toList().get(0)
                        + "}");
        return actionsToDo.stream().toList().get(0);
    }

    // --------------------------------------------------------
    // -------- Methods related to objectives analysis --------
    // --------------------------------------------------------

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
        } else if (pandaObjectives.size() == 1) {
            // Calculate whether if it is worth redeeming the panda objective
            boolean shouldRedeemPandaObjective =
                    analyzeIfShouldRedeemLastPandaObjective(
                            botState, history, (PandaObjective) pandaObjectives.get(0));
            if (shouldRedeemPandaObjective) {
                return new RedeemObjectiveAction(pandaObjectives.get(0));
            }
        }
        return null;
    }

    /**
     * This method is used to calculate if the bot can win by redeeming its last panda objective
     *
     * @param botState the bot state
     * @param history the history
     * @param pandaObjective the panda objective to redeem
     * @return true if it is worth redeeming the panda objective, false otherwise
     */
    public boolean analyzeIfShouldRedeemLastPandaObjective(
            BotState botState, History history, PandaObjective pandaObjective) {
        // Verify that it is the last objective
        int objectiveToComplete =
                GameEngine.DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN.get(
                        history.getBotManagerUUIDs().size() + 1);
        if (botState.getRedeemedObjectives().size() + 1 != objectiveToComplete) {
            return false;
        }

        // Retrieve the last HistoryItem from the history for each bot
        List<HistoryItem> listOfLastHistoryItem = history.getLatestHistoryItem();

        // Calculate the score for each bot
        List<Integer> listOfBotScores = new ArrayList<>();
        for (HistoryItem historyItem : listOfLastHistoryItem) {
            listOfBotScores.add(
                    historyItem.redeemedObjectives().stream().mapToInt(Objective::getPoints).sum());
        }

        // Calculate number of points counting the last panda objective as well as the Emperor's
        // bonus
        int ifRedeemedBotScore =
                pandaObjective.getPoints()
                        + botState.getObjectiveScore()
                        + EmperorObjective.EMPEROR_BONUS;

        // If the current bot has the highest score, redeem the panda objective
        return ifRedeemedBotScore > Collections.max(listOfBotScores) + ARBITRARY_MARGIN;
    }

    // --------------------------------------------------------
    // ---- Methods related to irrigation channels analysis ---
    // --------------------------------------------------------

    /**
     * This method will add the edge position to the irrigationToPlace list so that the bot knows
     * that he has to place them in order to complete is pattern objectiv
     *
     * @param board the board
     * @param botState the bot state
     */
    public void analyzeIrrigationToPlaceToCompletePatternObjective(Board board, BotState botState) {
        // Verify that the bot has
        // - a place irrigation action in its inventory
        // - at least one irrigation to place
        // - at least one pattern objective that is not already completed
        if (!allPatternObjectivesAreCompleted(botState)) {
            // Get all the shapes that could be completed by placing an irrigation
            List<List<Shape>> candidateShapes = getCandidateShapes(board, botState);
            console.displayDebug("BIG BRAIN MODE - candidateShapes: " + candidateShapes);

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
                console.displayDebug("BIG BRAIN MODE - irrigationToPlace: " + irrigationToPlace);
            }
        }
    }

    /**
     * Find all the current pattern objectives
     *
     * @param botState the bot state
     * @return the list of pattern objectives
     */
    public List<Objective> getCurrentPatternObjectives(BotState botState) {
        return botState.getObjectives().stream()
                .filter(PatternObjective.class::isInstance)
                .toList();
    }

    /**
     * Get all the shape matching the patterns on the current pattern objectives
     *
     * @param board the board
     * @param botState the bot state
     * @return the list of shapes matching the pattern objectives
     */
    public List<List<Shape>> getCandidateShapes(Board board, BotState botState) {
        List<PatternObjective> patternObjectives =
                botState.getObjectives().stream()
                        .filter(PatternObjective.class::isInstance)
                        .map(PatternObjective.class::cast)
                        .toList();

        console.displayDebug("BIG BRAIN MODE - patternObjectives: " + patternObjectives);

        List<List<Shape>> matchedPatterns = new ArrayList<>();
        for (PatternObjective patternObjective : patternObjectives) {
            matchedPatterns.add(patternObjective.getPattern().match(board, true));
        }

        return matchedPatterns;
    }

    public List<Shape> getIncompleteCandidateShapes(
            Board board, BotState botState, PatternObjective patternObjective) {
        console.displayDebug(
                "BIG BRAIN MODE - getIncompleteCandidateShapes - patternObjective: "
                        + patternObjective);

        // List<Shape> incompleteMatchingShape = patternObjective.getPattern().matchRatio(board,
        // true);
        return null;
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

    // --------------------------------------------------------
    // ----- Methods related to shape completion analysis -----
    // --------------------------------------------------------

    public Pair<Tile, PositionVector> analyzeTileToPlaceToCompleteShapeOfPatternObjective(
            Board board, BotState botState) {
        List<Tile> availableTilesFromDeck = board.peekTileDeck();
        List<PatternObjective> patternObjectives =
                getCurrentPatternObjectives(botState).stream()
                        .map(PatternObjective.class::cast)
                        .toList();

        List<Shape> candidateShapesToComplete = new ArrayList<>();
        for (PatternObjective patternObjective : patternObjectives) {
            List<Shape> incompleteShapes =
                    getIncompleteCandidateShapes(board, botState, patternObjective);
            int patternObjectiveSize = patternObjective.getPattern().getElements().size();

            for (Shape shape : incompleteShapes) {
                if (shape.getElements().size() == patternObjectiveSize - 1) {
                    candidateShapesToComplete.add(shape);
                }
            }
        }

        return null;
    }
}

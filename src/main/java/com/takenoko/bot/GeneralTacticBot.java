package com.takenoko.bot;

import static com.takenoko.bot.irrigation.pathfinding.IrrigationPathFinding.getShortestIrrigationPath;

import com.takenoko.actions.Action;
import com.takenoko.actions.irrigation.PlaceIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.layers.tile.Tile;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PandaObjective;
import com.takenoko.objective.PatternObjective;
import com.takenoko.shape.Shape;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.WeatherFactory;
import java.util.*;

public class GeneralTacticBot implements Bot {

    private final FullRandomBot fullRandomBot = new FullRandomBot();
    private final List<EdgePosition> irrigationToPlace = new ArrayList<>();

    @Override
    public Action chooseAction(Board board, BotState botState) {
        /*
         * Intelligence when having the choice of the meteo to either :
         * - Choose the sun because it is the best move
         * - Choose the wind because it allows the bot to win next move
         */
        if (botState.getAvailableActions().contains(ChooseAndApplyWeatherAction.class)) {
            // TODO: add some logic to choose the weather, if you can win by choosing WIND, do it
            return new ChooseAndApplyWeatherAction(WeatherFactory.SUNNY.createWeather());
        }

        /*
         * Intelligence to complete a PatterObjective by adding irrigation channels
         */
        if (botState.getAvailableActions().contains(ChooseAndApplyWeatherAction.class)) {
            // Because it is empty, we have to analyze the board to find the best irrigation path
            if (irrigationToPlace.isEmpty()) {
                analyzeIrrigationToPlaceToCompletePatternObjective(board, botState);
            }
            // If there is a path to complete the objective, we place the first edge
            if (!irrigationToPlace.isEmpty()) {
                return new PlaceIrrigationFromInventoryAction(irrigationToPlace.remove(0));
            }
            // TODO: What happens if we have no irrigation to place
        }

        /*
         * Intelligence to not redeem a PandaObjective if it is the last one in his hand, except if that makes it win
         */
        if (botState.getAvailableActions().contains(RedeemObjectiveAction.class)) {
            return analyzeObjectivesToRedeem(botState);
        }

        return fullRandomBot.chooseAction(board, botState);
    }

    // --------------------------------------------------------
    // -------- Methods related to objectives analysis --------
    // --------------------------------------------------------

    public RedeemObjectiveAction analyzeObjectivesToRedeem(BotState botState) {
        /*
         * If we have only one panda objective, do not redeem it
         * If we have any other objective, redeem any of them
         */
        List<Objective> pandaObjectives =
                botState.getObjectives().stream().filter(PandaObjective.class::isInstance).toList();

        List<Objective> otherObjectives =
                botState.getObjectives().stream()
                        .filter(objective -> !(objective instanceof PandaObjective))
                        .toList();

        if (pandaObjectives.size() > 1) {
            return new RedeemObjectiveAction(botState.getObjectives().get(0));
        } else if (pandaObjectives.size() == 1) {
            // TODO: add some logic to redeem the panda objective if it makes it win
            return new RedeemObjectiveAction(otherObjectives.get(0));
        } else {
            // TODO: What happens if we have no objective to redeem ?
            return null;
        }
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
        if (botState.getAvailableActions().contains(PlaceIrrigationAction.class)
                && botState.getInventory().getIrrigationChannelsCount() > 0
                && !allPatternObjectivesAreCompleted(botState)) {
            // Get all the shapes that could be completed by placing an irrigation
            List<Shape> candidateShapes = getCandidateShape(board, botState);

            // If it is not empty, be intelligent
            if (!candidateShapes.isEmpty()) {
                for (Shape shape : candidateShapes) {
                    // Get the position vector and tile of the shape
                    Map<PositionVector, Tile> pair = shape.getElements();
                    // Find the tiles that are not irrigated yet
                    List<PositionVector> candidateTilesToIrrigate =
                            pair.keySet().stream()
                                    .filter(tile -> !board.isIrrigatedAt(tile))
                                    .toList();
                    // Find which irrigation to place to irrigate all the tiles of the shape
                    List<EdgePosition> possibleEdgePositions =
                            getShortestIrrigationPath(candidateTilesToIrrigate, board);

                    // Verify that the number of irrigation needed does not exceed the number of
                    // irrigation available
                    if (botState.getInventory().getIrrigationChannelsCount()
                            > possibleEdgePositions.size()) {
                        // Clear the previous irrigation to place list
                        irrigationToPlace.clear();
                        // Add all the edge position to the list of irrigation to place
                        irrigationToPlace.addAll(possibleEdgePositions);
                        // Break the loop because we only consider the first shape found
                        break;
                    }
                }
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
    public List<Shape> getCandidateShape(Board board, BotState botState) {
        List<PatternObjective> patternObjectives =
                botState.getObjectives().stream()
                        .filter(PatternObjective.class::isInstance)
                        .map(PatternObjective.class::cast)
                        .toList();

        List<Shape> matchedPatterns = new ArrayList<>();
        for (PatternObjective patternObjective : patternObjectives) {
            matchedPatterns = patternObjective.getPattern().match(board);
        }

        return matchedPatterns;
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
}

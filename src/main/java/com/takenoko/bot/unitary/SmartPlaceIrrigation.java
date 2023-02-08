package com.takenoko.bot.unitary;

import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.bot.irrigation.pathfinding.IrrigationPathFinding;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.layers.tile.Tile;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PatternObjective;
import com.takenoko.shape.Shape;
import com.takenoko.vector.PositionVector;
import java.util.*;

public class SmartPlaceIrrigation extends PriorityBot {
    private final List<EdgePosition> irrigationToPlace = new ArrayList<>();

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // Complete a PatternObjective by adding irrigation channels
        if (botState.getAvailableActions().contains(PlaceIrrigationFromInventoryAction.class)) {
            // Because it is empty, we have to analyze the board to find the best irrigation path
            if (irrigationToPlace.isEmpty()) {
                analyzeIrrigationToPlaceToCompletePatternObjective(board, botState);
            }
            // If there is a path to complete the objective, we place the first edge
            if (!irrigationToPlace.isEmpty()) {
                this.addActionWithPriority(
                        new PlaceIrrigationFromInventoryAction(irrigationToPlace.remove(0)),
                        DEFAULT_PRIORITY);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartPlaceIrrigation that = (SmartPlaceIrrigation) o;
        return Objects.equals(irrigationToPlace, that.irrigationToPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), irrigationToPlace);
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
                                IrrigationPathFinding.getShortestIrrigationPath(
                                        candidateTilesToIrrigate, board);

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

    public List<PatternObjective> getCurrentPatternObjectives(BotState botState) {
        return botState.getObjectives().stream()
                .filter(PatternObjective.class::isInstance)
                .map(PatternObjective.class::cast)
                .toList();
    }
}

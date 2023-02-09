package com.takenoko.bot.unitary;

import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.tile.PlaceTileWithImprovementAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PatternObjective;
import com.takenoko.shape.Shape;
import com.takenoko.vector.PositionVector;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmartPattern extends PriorityBot {
    @Override
    protected void fillAction(
            Board board,
            BotState botState,
            History history) { // Complete the shape of the current PatternObjective
        if (botState.getAvailableActions().contains(PlaceTileAction.class)
                || botState.getAvailableActions().contains(PlaceTileWithImprovementAction.class)) {
            Map<PatternObjective, List<Shape>> tileToPlaceWithPosition =
                    analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective(board, botState);

            List<Tile> tilesDeck = board.peekTileDeck();

            for (Map.Entry<PatternObjective, List<Shape>> entry :
                    tileToPlaceWithPosition.entrySet()) {
                for (Shape shape : entry.getValue()) {
                    Map.Entry<PositionVector, Tile> tileToPlace =
                            shape.getElements().entrySet().stream()
                                    .filter(
                                            e ->
                                                    (tilesDeck.stream()
                                                                    .anyMatch(
                                                                            t ->
                                                                                    t.getColor()
                                                                                            .equals(
                                                                                                    e.getValue()
                                                                                                            .getColor()))
                                                            && board.getAvailableTilePositions()
                                                                    .contains(e.getKey())))
                                    .findFirst()
                                    .orElse(null);
                    if (tileToPlace != null) {

                        Tile tile =
                                tilesDeck.stream()
                                        .filter(
                                                t ->
                                                        t.getColor()
                                                                .equals(
                                                                        tileToPlace
                                                                                .getValue()
                                                                                .getColor()))
                                        .findFirst()
                                        .orElseThrow();

                        // We add both types of action to the bot. We consider that the wrong one
                        // will be
                        // sorted by the priority bot
                        this.addActionWithPriority(
                                new PlaceTileAction(tile, tileToPlace.getKey()),
                                calculatePriority(entry, shape));

                        // Verify that the tile to place does not already have an improvement
                        if (tile.getImprovement().isEmpty()) {
                            // We force the bot to place the watershed if he has one
                            if (botState.getInventory().hasImprovement(ImprovementType.WATERSHED)) {
                                this.addActionWithPriority(
                                        new PlaceTileWithImprovementAction(
                                                tile,
                                                tileToPlace.getKey(),
                                                ImprovementType.WATERSHED),
                                        calculatePriority(entry, shape));
                            } else if (botState.getInventory().hasImprovement()) {
                                // Otherwise place a """random""" one
                                this.addActionWithPriority(
                                        new PlaceTileWithImprovementAction(
                                                tile,
                                                tileToPlace.getKey(),
                                                botState.getInventory()
                                                        .getInventoryImprovements()
                                                        .get(0)),
                                        calculatePriority(entry, shape));
                            }
                        }
                    }
                }
            }
        }
    }

    public static double calculatePriority(
            Map.Entry<PatternObjective, List<Shape>> entry, Shape shape) {
        return entry.getKey().getPoints()
                * (shape.getElements().size()
                        / (double) entry.getKey().getPattern().getElements().size());
    }

    public Map<PatternObjective, List<Shape>>
            analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective(
                    Board board, BotState botState) {
        // Get all the pattern objectives of the bot
        Stream<PatternObjective> patternObjectives =
                getCurrentPatternObjectives(botState).stream()
                        .sorted(Comparator.comparing(Objective::getPoints).reversed());

        // Get all the shapes that could be completed by placing a tile
        Map<PatternObjective, List<Shape>> uncompletedSubsetOfShapes =
                patternObjectives
                        .map(
                                patternObjective ->
                                        Map.entry(
                                                patternObjective,
                                                patternObjective.getShapeToCompletePatternObjective(
                                                        board)))
                        .filter(entry -> !entry.getValue().isEmpty())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Get the available tiles to place
        List<Tile> deckAvailableTiles = board.peekTileDeck();
        List<TileColor> deckAvailableColors =
                deckAvailableTiles.stream().map(Tile::getColor).toList();

        return uncompletedSubsetOfShapes.entrySet().stream()
                .map(
                        entrySet ->
                                Map.entry(
                                        entrySet.getKey(),
                                        entrySet.getValue().stream()
                                                .filter(
                                                        shape ->
                                                                shape
                                                                        .getElements()
                                                                        .entrySet()
                                                                        .stream()
                                                                        .anyMatch(
                                                                                vectorTileEntry ->
                                                                                        deckAvailableColors
                                                                                                        .contains(
                                                                                                                vectorTileEntry
                                                                                                                        .getValue()
                                                                                                                        .getColor())
                                                                                                && board.getAvailableTilePositions()
                                                                                                        .contains(
                                                                                                                vectorTileEntry
                                                                                                                        .getKey())))
                                                .toList()))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<PatternObjective> getCurrentPatternObjectives(BotState botState) {
        return botState.getNotAchievedObjectives().stream()
                .filter(PatternObjective.class::isInstance)
                .map(PatternObjective.class::cast)
                .toList();
    }
}

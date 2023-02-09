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
import org.apache.commons.lang3.tuple.Pair;

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



            for (Map.Entry<PatternObjective, List<Shape>> entry : tileToPlaceWithPosition.entrySet()) {
                for (Shape shape : entry.getValue()) {
                    for (Map.Entry<PositionVector, Tile> tile : shape.getElements().entrySet()) {

                    }
                }
            }



            if (tileToPlaceWithPosition != null) {
                // We add both types of action to the bot. We consider that the wrong one will be
                // sorted by the priority bot
                this.addActionWithPriority(
                        new PlaceTileAction(
                                tileToPlaceWithPosition.getRight(),
                                tileToPlaceWithPosition.getLeft()),
                        DEFAULT_PRIORITY);

                // Verify that the tile to place does not already have an improvement
                if (tileToPlaceWithPosition.getRight().getImprovement().isEmpty()) {
                    // We force the bot to place the watershed if he has one
                    if (botState.getInventory().hasImprovement(ImprovementType.WATERSHED)) {
                        this.addActionWithPriority(
                                new PlaceTileWithImprovementAction(
                                        tileToPlaceWithPosition.getRight(),
                                        tileToPlaceWithPosition.getLeft(),
                                        ImprovementType.WATERSHED),
                                DEFAULT_PRIORITY);
                    } else if (botState.getInventory().hasImprovement()) {
                        // Otherwise place a """random""" one
                        this.addActionWithPriority(
                                new PlaceTileWithImprovementAction(
                                        tileToPlaceWithPosition.getRight(),
                                        tileToPlaceWithPosition.getLeft(),
                                        botState.getInventory().getInventoryImprovements().get(0)),
                                DEFAULT_PRIORITY);
                    }
                }
            }
        }
    }

    public Map<PatternObjective, List<Shape>> analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective(
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
                                        Map.entry(patternObjective,
                                                patternObjective
                                                        .getShapeToCompletePatternObjective(board))
                        )
                        .filter(entry -> !entry.getValue().isEmpty())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Get the available tiles to place
        List<Tile> deckAvailableTiles = board.peekTileDeck();
        List<TileColor> deckAvailableColors =
                deckAvailableTiles.stream().map(Tile::getColor).toList();

        return uncompletedSubsetOfShapes.entrySet().stream()
                        .map(
                                entrySet -> Map.entry(
                                        entrySet.getKey(),
                                        entrySet.getValue().stream().filter(
                                                        shape ->
                                                                shape.getElements().entrySet().stream()
                                                                        .anyMatch(
                                                                                vectorTileEntry ->
                                                                                        deckAvailableColors.contains(
                                                                                                vectorTileEntry.getValue().getColor())
                                                                                                && board.getAvailableTilePositions().contains(
                                                                                                vectorTileEntry.getKey())
                                                                        )
                                                )
                                                .toList()
                                )
                        )
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

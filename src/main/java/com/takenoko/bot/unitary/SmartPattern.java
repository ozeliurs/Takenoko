package com.takenoko.bot.unitary;

import com.takenoko.actions.Action;
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
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class SmartPattern extends PriorityBot {
    @Override
    public Action chooseAction(
            Board board,
            BotState botState,
            History history) { // Complete the shape of the current PatternObjective
        if (botState.getAvailableActions().contains(PlaceTileAction.class)
                || botState.getAvailableActions().contains(PlaceTileWithImprovementAction.class)) {
            Pair<PositionVector, Tile> tileToPlaceWithPosition =
                    analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective(board, botState);
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
        return super.chooseAction(board, botState, history);
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

    public List<PatternObjective> getCurrentPatternObjectives(BotState botState) {
        return botState.getObjectives().stream()
                .filter(PatternObjective.class::isInstance)
                .map(PatternObjective.class::cast)
                .toList();
    }
}

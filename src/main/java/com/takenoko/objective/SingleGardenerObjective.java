package com.takenoko.objective;

import static java.lang.Math.abs;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.*;

/**
 * Class SingleGardenerObjective represents a single gardener objective. A single gardener objective
 * is composed of a size, a color and an improvement type.
 */
public class SingleGardenerObjective extends Objective {
    private final int targetSize;
    private final TileColor targetColor;

    private final ImprovementType targetImprovementType;

    public SingleGardenerObjective(
            int targetSize,
            TileColor targetColor,
            ImprovementType targetImprovementType,
            int points) {
        super(ObjectiveTypes.GARDENER, ObjectiveState.NOT_ACHIEVED, points);
        this.targetSize = targetSize;
        this.targetImprovementType = targetImprovementType;
        this.targetColor = targetColor;
    }

    public SingleGardenerObjective(int targetSize, TileColor targetColor, int points) {
        this(targetSize, targetColor, ImprovementType.NONE, points);
    }

    @Override
    public void verify(Board board, BotState botState) {
        if (!getMatchingPositions(board).isEmpty()) {
            this.state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public void reset() {
        this.state = ObjectiveState.NOT_ACHIEVED;
    }

    private boolean match(Board board, PositionVector positionVector, Tile tile) {
        return board.getBambooAt(positionVector).getBambooCount() == targetSize
                && isTileEligible(tile);
    }

    private boolean isTileEligible(Tile tile) {
        /*
         * A tile is eligible if:
         * - It's color matches the target color, or is ANY
         * - It's improvement type matches the target improvement type, or is ANY
         */
        Optional<ImprovementType> tileImprovement = tile.getImprovement();
        return (tile.getColor() == targetColor || tile.getColor() == TileColor.ANY)
                && (tileImprovement.isPresent() && tileImprovement.get() == targetImprovementType
                        || tileImprovement.isPresent()
                                && tileImprovement.get() == ImprovementType.ANY);
    }

    public List<PositionVector> getEligiblePositions(Board board) {
        return board.getTiles().entrySet().stream()
                .filter(v -> isTileEligible(v.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<PositionVector> getMatchingPositions(Board board) {
        return board.getTiles().entrySet().stream()
                .filter(v -> match(board, v.getKey(), v.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public SingleGardenerObjective copy() {
        return new SingleGardenerObjective(
                targetSize, targetColor, targetImprovementType, getPoints());
    }

    @Override
    public float getCompletion(Board board, BotState botState) {
        return getEligiblePositions(board).stream()
                .max(Comparator.comparingInt(v -> board.getBambooAt(v).getBambooCount()))
                .map(v -> board.getBambooAt(v).getBambooCount())
                .map(integer -> 1 - ((float) abs(integer - targetSize) / targetSize))
                .orElse(0f);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleGardenerObjective that = (SingleGardenerObjective) o;
        return targetSize == that.targetSize
                && targetColor == that.targetColor
                && targetImprovementType == that.targetImprovementType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetSize, targetColor, targetImprovementType);
    }

    public int getTargetSize() {
        return targetSize;
    }

    @Override
    public String toString() {
        return "Single Gardener Objective <"
                + targetColor
                + " bamboo, "
                + targetSize
                + " high, "
                + targetImprovementType
                + " improvement>";
    }
}

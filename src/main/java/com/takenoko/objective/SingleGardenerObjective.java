package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
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
    public void verify(Board board, BotManager botManager) {
        if (!getMatchingPositions(board).isEmpty()) {
            this.state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public void reset() {
        this.state = ObjectiveState.NOT_ACHIEVED;
    }

    private boolean match(Board board, PositionVector positionVector, Tile tile) {
        if (!isTileEligible(board, positionVector, tile)) return false;

        return board.getBambooAt(positionVector).getBambooCount() == targetSize;
    }

    private boolean isTileEligible(Board board, PositionVector positionVector, Tile tile) {
        if ((targetColor.equals(TileColor.NONE) || !tile.getColor().equals(targetColor))
                && !targetColor.equals(TileColor.ANY)) {
            return false;
        }
        if ((!targetImprovementType.equals(ImprovementType.NONE)
                && !targetImprovementType.equals(ImprovementType.ANY))) {
            Optional<ImprovementType> improvementType = tile.getImprovement();
            return !(improvementType.isPresent()
                            && !improvementType.get().equals(targetImprovementType))
                    && board.getBambooAt(positionVector).getBambooCount() <= targetSize;
        }
        return board.getBambooAt(positionVector).getBambooCount() <= targetSize;
    }

    public List<PositionVector> getEligiblePositions(Board board) {
        return board.getTiles().entrySet().stream()
                .filter(v -> isTileEligible(board, v.getKey(), v.getValue()))
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
    public float getCompletion(Board board, BotManager botManager) {
        return getEligiblePositions(board).stream()
                .max(Comparator.comparingInt(v -> board.getBambooAt(v).getBambooCount()))
                .map(v -> board.getBambooAt(v).getBambooCount())
                .map(integer -> (float) integer / targetSize)
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
}

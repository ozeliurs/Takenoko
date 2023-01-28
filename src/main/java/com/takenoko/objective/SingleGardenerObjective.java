package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SingleGardenerObjective extends Objective {
    private final int targetSize;
    private final TileColor targetColor;

    private final ImprovementType targetImprovementType;

    public SingleGardenerObjective(int targetSize, TileColor targetColor, ImprovementType targetImprovementType) {
        super(ObjectiveTypes.GARDENER, ObjectiveState.NOT_ACHIEVED);
        this.targetSize = targetSize;
        this.targetImprovementType = targetImprovementType;
        if (targetColor == TileColor.ANY) {
            this.targetColor = null;
        } else {
            this.targetColor = targetColor;
        }
    }

    public SingleGardenerObjective(int targetSize, TileColor targetColor) {
        this(targetSize, targetColor, null);
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        board.getTiles()
                .forEach(
                        (positionVector, tile) -> {
                            if (match(board, positionVector, tile)) {
                                this.state = ObjectiveState.ACHIEVED;
                            }

                        });
    }

    private boolean match(Board board, PositionVector positionVector, Tile tile) {
        if (targetColor != null && !tile.getColor().equals(targetColor)) {
            return false;
        }
        if (targetImprovementType != null) {
            Optional<ImprovementType> improvementType = tile.getImprovement();
            if (improvementType.isPresent() && !improvementType.get().equals(targetImprovementType)) {
                return false;
            }
        }

        return board.getBambooAt(positionVector).getBambooCount() == targetSize;
    }

    @Override
    public void reset() {
        this.state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public Objective copy() {
        return new SingleGardenerObjective(targetSize, targetColor, targetImprovementType);
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        AtomicReference<Float> completion = new AtomicReference<>(0f);
        board.getTiles()
                .forEach(
                        (positionVector, tile) -> {
                            if (match(board, positionVector, tile)) {
                                completion.set(
                                        Math.max(
                                                completion.get(),
                                                board.getBambooAt(positionVector).getBambooCount()
                                                        / (float) this.targetSize));
                            }
                        });
        return Math.min(1, completion.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SingleGardenerObjective that = (SingleGardenerObjective) o;
        return targetSize == that.targetSize &&
                targetColor == that.targetColor &&
                targetImprovementType == that.targetImprovementType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), targetSize, targetColor, targetImprovementType);
    }
}

package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.TileColor;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ColoredBambooSizeObjective extends BambooSizeObjective {
    private final TileColor tileColor;

    public ColoredBambooSizeObjective(int size, TileColor tileColor) {
        super(size);
        this.tileColor = tileColor;
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        // iterate over all the bamboo stacks on the board
        // if one of them is bigger than the size of the objective, the objective is achieved
        board.getTiles()
                .forEach(
                        (positionVector, tile) -> {
                            if (board.getBambooAt(positionVector).getBambooCount() >= this.getSize()
                                    && tile.getColor() == tileColor) {
                                this.state = ObjectiveState.ACHIEVED;
                            }
                        });
    }

    @Override
    public ColoredBambooSizeObjective copy() {
        return new ColoredBambooSizeObjective(this.getSize(), this.tileColor);
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        AtomicReference<Float> completion = new AtomicReference<>(0f);
        board.getTiles()
                .forEach(
                        (positionVector, tile) -> {
                            if (tile.getColor() == tileColor) {
                                completion.set(
                                        Math.max(
                                                completion.get(),
                                                board.getBambooAt(positionVector).getBambooCount()
                                                        / (float) this.getSize()));
                            }
                        });
        return Math.min(1, completion.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ColoredBambooSizeObjective that = (ColoredBambooSizeObjective) o;
        return tileColor == that.tileColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tileColor);
    }
}

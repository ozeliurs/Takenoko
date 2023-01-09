package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class BambooSizeObjective extends Objective {
    private final int size;

    /**
     * BambooSizeObjective constructor.
     *
     * @param size the size of the bamboo to achieve.
     */
    protected BambooSizeObjective(int size) {
        super(ObjectiveTypes.BAMBOO_STACK, ObjectiveState.NOT_ACHIEVED);
        this.size = size;
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        // iterate over all the bamboo stacks on the board
        // if one of them is bigger than the size of the objective, the objective is achieved
        board.getTiles()
                .forEach(
                        (positionVector, tile) -> {
                            if (board.getBambooAt(positionVector).getBambooCount() >= size) {
                                this.state = ObjectiveState.ACHIEVED;
                            }
                        });
    }

    @Override
    public void reset() {
        this.state = ObjectiveState.NOT_ACHIEVED;
    }

    public BambooSizeObjective copy() {
        return new BambooSizeObjective(size);
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        AtomicReference<Float> completion = new AtomicReference<>((float) 0);
        board.getTiles()
                .forEach(
                        (positionVector, tile) ->
                                completion.set(
                                        Math.max(
                                                completion.get(),
                                                board.getBambooAt(positionVector).getBambooCount()
                                                        / (float) size)));
        return Math.min(1, completion.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BambooSizeObjective that = (BambooSizeObjective) o;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }
}

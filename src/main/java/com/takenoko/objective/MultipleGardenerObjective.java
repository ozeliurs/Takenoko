package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/** Objective is to complete a certain number of single gardener objectives. */
public class MultipleGardenerObjective extends Objective {

    SingleGardenerObjective objective;
    private final int numberOfTimes;

    public MultipleGardenerObjective(SingleGardenerObjective objective, int numberOfTimes) {
        super(ObjectiveTypes.BAMBOO_STACK, ObjectiveState.NOT_ACHIEVED);
        this.objective = objective;
        this.numberOfTimes = numberOfTimes;
    }

    public MultipleGardenerObjective(MultipleGardenerObjective multipleGardenerObjective) {
        this(multipleGardenerObjective.objective.copy(), multipleGardenerObjective.numberOfTimes);
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        if (this.objective.getMatchingPositions(board).size() >= numberOfTimes) {
            this.state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public void reset() {
        this.state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public MultipleGardenerObjective copy() {
        return new MultipleGardenerObjective(this);
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        // spotless:off
        List<Integer> bambooCounts =
                objective.getEligiblePositions(board).stream()
                        .sorted(Comparator.comparingInt(v -> board.getBambooAt((PositionVector) v).getBambooCount())
                                        .reversed())
                        .limit(numberOfTimes)
                        .map(v -> board.getBambooAt(v).getBambooCount())
                        .toList();
        Stream<Integer> bambooCountFilled =
                Stream.concat(
                        bambooCounts.stream(),
                        Stream.generate(() -> 0).limit((long) numberOfTimes - bambooCounts.size()));
        return 1 - ((float) bambooCountFilled
                                        .map(v -> Math.abs(v - objective.getTargetSize()))
                                        .reduce(0, Integer::sum)
                        / (numberOfTimes * objective.getTargetSize()));
        // spotless:on
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultipleGardenerObjective that = (MultipleGardenerObjective) o;
        return numberOfTimes == that.numberOfTimes && objective.equals(that.objective);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objective, numberOfTimes);
    }
}

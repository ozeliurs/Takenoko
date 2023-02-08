package com.takenoko.bot.unitary;

import com.takenoko.actions.improvement.DrawImprovementAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import java.util.Map;
import java.util.Objects;

/**
 * This bot will draw improvement if there is less than the number of improvement in the inventory.
 */
public class SmartDrawImprovement extends PriorityBot {
    private final Map<ImprovementType, Integer> improvementMap;

    /**
     * @param improvementMap the map of improvement and the number of improvement to draw.
     */
    public SmartDrawImprovement(Map<ImprovementType, Integer> improvementMap) {
        this.improvementMap = improvementMap;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        improvementMap.forEach(
                (improvementType, integer) -> {
                    if (botState.getInventory().getImprovementCount(improvementType) < integer) {
                        this.addActionWithPriority(
                                new DrawImprovementAction(improvementType), integer);
                    }
                });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartDrawImprovement that = (SmartDrawImprovement) o;
        return Objects.equals(improvementMap, that.improvementMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), improvementMap);
    }
}

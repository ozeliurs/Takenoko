package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.TileColor;
import java.util.EnumMap;
import java.util.Map;

/** The Panda objective. */
public class PandaObjective extends Objective {
    private final Map<TileColor, Integer> bambooTarget;

    @SafeVarargs
    public PandaObjective(Map.Entry<TileColor, Integer>... bambooTarget) {
        this(Map.ofEntries(bambooTarget));
    }

    public PandaObjective(Map<TileColor, Integer> bambooTarget) {
        super(ObjectiveTypes.PANDA, ObjectiveState.NOT_ACHIEVED);
        this.bambooTarget = bambooTarget;
    }

    public PandaObjective(PandaObjective pandaObjective) {
        super(pandaObjective.getType(), pandaObjective.getState());
        this.bambooTarget = new EnumMap<>(pandaObjective.bambooTarget);
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        bambooTarget.entrySet().stream()
                .filter(
                        entry ->
                                botManager.getInventory().getBambooCount(entry.getKey())
                                        >= entry.getValue())
                .findFirst()
                .ifPresent(entry -> state = ObjectiveState.ACHIEVED);
    }

    @Override
    public void reset() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public Objective copy() {
        return new PandaObjective(this);
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        return bambooTarget.entrySet().stream()
                        .map(
                                entry ->
                                        (float)
                                                        botManager
                                                                .getInventory()
                                                                .getBambooCount(entry.getKey())
                                                / entry.getValue())
                        .reduce(0f, Float::sum)
                / bambooTarget.size();
    }
}

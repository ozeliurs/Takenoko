package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/** Interface for the weather. */
public abstract class Weather {
    /**
     * Code to be executed when the weather is rolled and modifiers applied.
     *
     * @return List of actions to be executed after the weather is applied.
     */
    public abstract List<Class<? extends Action>> apply(Board board, BotManager botManager);

    public abstract void revert(Board board, BotManager botManager);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}

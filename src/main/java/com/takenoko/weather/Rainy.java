package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.bamboo.GrowBambooAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/**
 * A fine rain nourishes the young bamboo shoots. The player may place a Bamboo section on the
 * irrigated plot of his choice, up to a limit of four sections per plot.
 */
public class Rainy implements Weather {
    @Override
    public List<Class<? extends Action>> apply(Board board, BotManager botManager) {
        board.setWeather(this);
        if (board.getGrowablePositions().isEmpty()) {
            botManager.displayMessage("No bamboo to grow");
            return List.of();
        }
        return List.of(GrowBambooAction.class);
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }

    @Override
    public String toString() {
        return "Rainy";
    }
}

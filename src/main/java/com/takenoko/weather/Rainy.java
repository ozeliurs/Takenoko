package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.bamboo.GrowBambooAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

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
}

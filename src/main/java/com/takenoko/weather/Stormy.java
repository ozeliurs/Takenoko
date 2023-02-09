package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/**
 * The sky rumbles and lightning strikes, frightening the panda. The player can put the panda on the
 * plot of his choice. To recover from his fear, the shy animal eats a section of bamboo.
 */
public class Stormy extends Weather {

    @Override
    public List<Class<? extends Action>> apply(Board board, BotManager botManager) {
        board.setWeather(this);
        if (!board.getTilesWithoutPond().isEmpty()) {
            return List.of(ForcedMovePandaAction.class);
        }
        botManager.displayMessage(
                botManager.getName()
                        + " there aren't enough tiles to move the panda. You can choose the weather"
                        + " you want.");
        return List.of(ChooseAndApplyWeatherAction.class);
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }
}

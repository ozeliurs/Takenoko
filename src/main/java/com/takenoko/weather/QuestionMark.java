package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

public class QuestionMark implements Weather {
    @Override
    public List<Class<? extends Action>> apply(Board board, BotManager botManager) {
        board.setWeather(this);
        return List.of(ChooseAndApplyWeatherAction.class);
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }

    @Override
    public String toString() {
        return "QuestionMark";
    }
}

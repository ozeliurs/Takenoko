package com.takenoko.bot.unitary;

import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

public class SmartDrawIrrigation extends PriorityBot {
    private static final int ARBITRARY_MAX_IRRIGATION_TO_CHOOSE_TO_DRAW = 3;

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        if (botState.getInventory().getIrrigationChannelsCount()
                < ARBITRARY_MAX_IRRIGATION_TO_CHOOSE_TO_DRAW) {
            this.addActionWithPriority(new DrawIrrigationAction(), DEFAULT_PRIORITY);
        }
    }
}

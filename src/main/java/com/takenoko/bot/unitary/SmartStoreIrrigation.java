package com.takenoko.bot.unitary;

import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

public class SmartStoreIrrigation extends PriorityBot {

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        this.addActionWithPriority(new StoreIrrigationInInventoryAction(), DEFAULT_PRIORITY);
    }
}

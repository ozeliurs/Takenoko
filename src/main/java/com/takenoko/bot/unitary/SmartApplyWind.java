package com.takenoko.bot.unitary;

import com.takenoko.actions.Action;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

public class SmartApplyWind extends PriorityBot {
    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        // TODO: Implement a smart choice when doing the wind action | Priority : 0
        return super.chooseAction(board, botState, history);
    }
}

package com.takenoko.inventory;

import com.takenoko.bot.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public class CollectBambooAction implements Action {

    @Override
    public void execute(Board board, BotManager botManager) {
        botManager.getInventory().getBambooStack().addBamboo();
        botManager.displayMessage(botManager + " collected one bamboo");
    }
}

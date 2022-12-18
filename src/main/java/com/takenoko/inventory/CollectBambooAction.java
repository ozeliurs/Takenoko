package com.takenoko.inventory;

import com.takenoko.bot.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public class CollectBambooAction implements Action {

    @Override
    public void execute(Board board, BotManager botManager) {
        botManager.getInventory().getBambooStack().collectBamboo();
        botManager.displayMessage(botManager.getName() + " collected one bamboo");
    }
}

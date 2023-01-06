package com.takenoko.bot;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;

/** The Bot interface will allow the class implementing it to interact with the game and play. */
public interface Bot {
    /**
     * This method will allow the player to choose an action to execute.
     *
     * @param board The board of the game.
     * @param botState The state of the bot.
     * @return The action chosen by the player.
     */
    Action chooseAction(Board board, BotState botState);
}

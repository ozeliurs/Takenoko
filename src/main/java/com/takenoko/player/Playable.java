package com.takenoko.player;

import com.takenoko.engine.Board;

/**
 * The Playable interface will allow the class implementing it to interact with the game and play.
 */
public interface Playable {
    /**
     * This method will allow the player to choose an action to execute.
     *
     * @param board The board of the game.
     * @return The action chosen by the player.
     */
    Action chooseAction(Board board);
}

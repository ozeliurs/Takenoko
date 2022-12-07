package com.takenoko;

import com.takenoko.ui.ConsoleUserInterface;

public class Bot implements Playable {
    private final Board board;
    private final ConsoleUserInterface consoleUserInterface;

    /**
     * Constructor for the Bot class. Instantiate a user interface for the bot.
     * @param board the board of the game the bot is currently on
     */
    public Bot(Board board) {
        this.board = board;
        this.consoleUserInterface = new ConsoleUserInterface();
    }

    /**
     * This method place on the board the only available tile.
     */
    @Override
    public void placeTile() {
        try {
            board.placeTile(new Tile());
            consoleUserInterface.displayMessage("The bot placed a tile on the board.");
        }
        catch (IllegalStateException e) {
            consoleUserInterface.displayMessage(e.getMessage());
        }
    }
}

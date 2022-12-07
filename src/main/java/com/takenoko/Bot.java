package com.takenoko;

import com.takenoko.ui.ConsoleUserInterface;

public class Bot implements Playable {
    private final Board board;
    private final ConsoleUserInterface consoleUserInterface;

    public Bot(Board board) {
        this.board = board;
        this.consoleUserInterface = new ConsoleUserInterface();
    }

    @Override
    public void placeTile() {
        try {
            board.placeTile(new Tile());
        }
        catch (IllegalStateException e) {
            consoleUserInterface.displayMessage(e.getMessage());
        }
    }
}

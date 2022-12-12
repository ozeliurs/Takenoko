package com.takenoko;

import com.takenoko.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        gameEngine.newGame();
        gameEngine.startGame();
        gameEngine.playGame();
        gameEngine.endGame();
    }
}

package com.takenoko;

public class Main {
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        gameEngine.newGame();
        gameEngine.startGame();
        gameEngine.playGame();
        gameEngine.endGame();
    }
}

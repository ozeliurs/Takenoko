package com.takenoko.engine;

/** The EndGameState enum is used to represent the different states of the game as when it ends. */
public enum EndGameState {
    /**
     * WIN_WITH_OBJECTIVE_POINTS state is used when the game is won by the player with the most
     * objective points.
     */
    WIN_WITH_OBJECTIVE_POINTS,
    /**
     * WIN_WITH_PANDA_OBJECTIVE_POINTS state is used when the game is tied on objective points but
     * won by the player with the most panda objective points.
     */
    WIN_WITH_PANDA_OBJECTIVE_POINTS,
    /**
     * TIE state is used when the game is tied on objective points and tied on panda objective
     * points.
     */
    TIE,
}

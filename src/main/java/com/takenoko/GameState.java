package com.takenoko;

/** The GameState enum is used to represent the different states of the game as it progresses. */
public enum GameState {
    /** INITIALIZED state is used when the game is created but not started yet. */
    INITIALIZED,
    /** READY state is used when the game is ready to be played. */
    READY,
    /** PLAYING state is used when the game is currently being played. */
    PLAYING,
    /** FINISHED state is used when the game is finished. */
    FINISHED
}

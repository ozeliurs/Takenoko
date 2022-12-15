package com.takenoko.actors;

import com.takenoko.engine.Board;

public class ActorsManager {
    private final Panda panda;

    public ActorsManager(Board board) {
        this.panda = new Panda(board);
    }

    public Panda getPanda() {
        return panda;
    }
}

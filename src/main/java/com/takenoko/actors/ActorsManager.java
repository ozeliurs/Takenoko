package com.takenoko.actors;

import com.takenoko.actors.panda.Panda;
import com.takenoko.engine.Board;

public class ActorsManager {
    private final Panda panda;

    public ActorsManager(Panda panda) {
        this.panda = panda;
    }

    public ActorsManager(Board board) {
        this(new Panda(board));
    }

    public Panda getPanda() {
        return panda;
    }
}

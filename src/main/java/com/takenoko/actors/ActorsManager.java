package com.takenoko.actors;

import com.takenoko.actors.panda.Panda;

public class ActorsManager {
    private final Panda panda;

    public ActorsManager(Panda panda) {
        this.panda = panda;
    }

    public ActorsManager() {
        this(new Panda());
    }

    public Panda getPanda() {
        return panda;
    }
}

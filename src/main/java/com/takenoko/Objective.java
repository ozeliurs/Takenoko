package com.takenoko;

public interface Objective {

    void verify(Board board);

    boolean isAchieved();
}

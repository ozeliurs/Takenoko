package com.takenoko;

public interface Objective {

    public void setAction(int counterGoal);

    public void incrementCounter();

    public boolean isAchieved();
}

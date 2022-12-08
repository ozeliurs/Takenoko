package com.takenoko;

public class PlaceTileObjective implements Objective {
    private int counterGoal = 0;
    private ObjectiveTypes type = ObjectiveTypes.TileOrientedObjective;
    private boolean status;
    private int counter;

    public PlaceTileObjective(int counterGoal) {
        this.counterGoal = counterGoal;
        status = false;
        counter = 0;
    }

    public PlaceTileObjective() {}

    public void setAction(int counterGoal) {
        this.counterGoal = counterGoal;
    }

    /**
     * This method will be used each time the bot adds a tile (and later on eat/plant a bamboo) so
     * we can track the progression towards achieving the objective
     */
    public void incrementCounter() {
        counter++;
    }

    /**
     * this methods is used to check whether the objective has been achieved yet
     *
     * @return
     */
    public boolean isAchieved() {
        status = (counter == counterGoal);
        return status;
    }

    public String toString() {
        String s = (counterGoal > 1 ? "s" : "");
        return "You must place " + counterGoal + " tile" + s;
    }
}

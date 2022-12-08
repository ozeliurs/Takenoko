package com.takenoko;

import java.util.List;

public class Bot implements Playable {
    private Objective objective;

    public Bot(){
        objective=new Objective();
    }

    public Objective getObjective(){
        return objective;
    }

    /**
     * This method is used to assign an Objective to the bot
     * @param assignedObjectiveType
     * @param goal
     */
    public void setObjective(String assignedObjectiveType,int goal){
        objective.setAction(goal, assignedObjectiveType);
    }

    /**
     * This method return the chosen tile to place on the board.
     *
     * @param possibleTiles The list of possible tiles to place.
     * @return The chosen tile to place.
     */
    @Override
    public Tile chooseTileToPlace(List<Tile> possibleTiles) {
        return possibleTiles.get(0);
    }
}

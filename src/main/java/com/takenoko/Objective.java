package com.takenoko;

public class Objective {
    private int counterGoal;
    private String type;
    private boolean status;
    private int counter;

    public Objective(int counterGoal,String type){
        this.counterGoal=counterGoal;
        this.type=type;
        status=false;
        counter=0;
    }

    public Objective(){
        this(0,"to be determined");
    }

    public void setAction(int counterGoal,String type){
        this.type=type;
        this.counterGoal=counterGoal;
    }

    /**
     * This method will be used each time the bot adds a tile (and later on eat/plant a bamboo) so we can track the progression
     * towards achieving the objective
     */
    public void incrementCounter(){
        counter++;
    }

    /**
     * this methods is used to check whether the objective has been achieved yet
     * @return
     */
    public boolean isAchieved(){
        status=(counter==counterGoal);
        return status;
    }

    public String toString(){
        return "You must place "+counterGoal+" "+type;
    }
}

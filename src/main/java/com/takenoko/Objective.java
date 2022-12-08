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

    public void incrementCounter(){
        counter++;
    }

    public boolean isAchieved(){
        status=(counter==counterGoal);
        return status;
    }

    public String toString(){
        return "You must place "+counterGoal+" "+type;
    }
}

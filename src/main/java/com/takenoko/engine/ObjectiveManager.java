package com.takenoko.engine;

import com.takenoko.objective.Objective;
import com.takenoko.objective.PandaObjective;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectiveManager {
    private final List<Objective> objectives;
    private final List<Objective> achievedObjectives;
    private final List<Objective> redeemedObjectives;

    public ObjectiveManager() {
        this.objectives = new ArrayList<>();
        this.achievedObjectives = new ArrayList<>();
        this.redeemedObjectives = new ArrayList<>();
    }

    public ObjectiveManager(ObjectiveManager objectiveManager) {
        this.objectives = new ArrayList<>(objectiveManager.objectives);
        this.achievedObjectives = new ArrayList<>(objectiveManager.achievedObjectives);
        this.redeemedObjectives = new ArrayList<>(objectiveManager.redeemedObjectives);
    }

    /**
     * Get the current Objectives of the bot
     *
     * @return Objectives
     */
    public List<Objective> getObjectives() {
        return new ArrayList<>(objectives);
    }

    /**
     * get the score of the achieved objectives
     *
     * @return the score of the achieved objectives
     */
    public int getObjectiveScore() {
        return redeemedObjectives.stream().mapToInt(Objective::getPoints).sum();
    }

    /**
     * Get the list of the redeemed objectives
     *
     * @return the list of the redeemed objectives
     */
    public List<Objective> getRedeemedObjectives() {
        return new ArrayList<>(redeemedObjectives);
    }

    /**
     * get the list of achieved objectives
     *
     * @return the list of achieved objectives
     */
    public List<Objective> getAchievedObjectives() {
        return new ArrayList<>(achievedObjectives);
    }

    public ObjectiveManager getObjectiveManager() {
        return this.copy();
    }

    /**
     * for each objective, check if it is achieved
     *
     * @param board the board
     * @param botState the bot manager
     */
    public void verifyObjectives(Board board, BotState botState) {
        for (Objective objective : objectives) {
            objective.verify(board, botState);
        }
        for (Objective objective : achievedObjectives) {
            objective.verify(board, botState);
        }
    }

    /**
     * Set the current Objective of the bot
     *
     * @param objective the objectives
     */
    public void addObjective(Objective objective) {
        this.objectives.add(objective);
    }

    /**
     * update the objectives
     *
     * @param board the board
     * @param botState the bot manager
     */
    public void updateObjectives(Board board, BotState botState) {
        verifyObjectives(board, botState);

        // If objective is achieved, add it to the list of achieved objectives
        List<Objective> toAchieve = new ArrayList<>();
        for (Objective objective : objectives) {
            if (objective.isAchieved()) {
                toAchieve.add(objective);
            }
        }
        for (Objective objective : toAchieve) {
            setObjectiveAchieved(objective);
        }

        // If objective is no more achievable, remove it from the list of objectives
        List<Objective> toRemove = new ArrayList<>();
        for (Objective objective : achievedObjectives) {
            if (!objective.isAchieved()) {
                toRemove.add(objective);
            }
        }
        for (Objective objective : toRemove) {
            setObjectiveNotAchieved(objective);
        }
    }

    /**
     * set an objective as not achieved
     *
     * @param objective the objective
     */
    public void setObjectiveNotAchieved(Objective objective) {
        this.achievedObjectives.remove(objective);
        this.objectives.add(objective);
    }

    /**
     * set an objective as achieved
     *
     * @param objective the objective
     */
    public void setObjectiveAchieved(Objective objective) {
        this.objectives.remove(objective);
        this.achievedObjectives.add(objective);
    }

    /**
     * redeem an objective
     *
     * @param objective the objective
     */
    public void redeemObjective(Objective objective, BotState botState) {
        this.achievedObjectives.remove(objective);
        if (objective instanceof PandaObjective pandaObjective) {
            botState.getInventory().useBamboo(pandaObjective.getBambooTarget());
        }
        this.redeemedObjectives.add(objective);
    }

    /**
     * Returns the sum of the points of all the panda objectives
     *
     * @return the sum of the points of all the panda objectives
     */
    public int getPandaObjectiveScore() {
        return redeemedObjectives.stream()
                .filter(PandaObjective.class::isInstance)
                .mapToInt(Objective::getPoints)
                .sum();
    }

    /**
     * Return a copy of the current ObjectiveManager
     *
     * @return ObjectiveManager
     */
    public ObjectiveManager copy() {
        return new ObjectiveManager(this);
    }

    /** Reset all the attributes of the ObjectiveManager */
    public void reset() {
        this.objectives.clear();
        this.achievedObjectives.clear();
        this.redeemedObjectives.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveManager that = (ObjectiveManager) o;
        return Objects.equals(objectives, that.objectives)
                && Objects.equals(achievedObjectives, that.achievedObjectives)
                && Objects.equals(redeemedObjectives, that.redeemedObjectives);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectives, achievedObjectives, redeemedObjectives);
    }
}

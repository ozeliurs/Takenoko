package com.takenoko.objective;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmperorObjectiveTest {
    @Test
    @DisplayName("Gives two points")
    void givesTwoPoints() {
        EmperorObjective emperorObjective = new EmperorObjective();
        assertEquals(2, emperorObjective.getPoints());
    }

    @Test
    @DisplayName("Is always achieved")
    void isAlwaysAchieved() {
        EmperorObjective emperorObjective = new EmperorObjective();
        assertTrue(emperorObjective.isAchieved());
    }

    @Test
    @DisplayName("Is always completed")
    void isAlwaysCompleted() {
        EmperorObjective emperorObjective = new EmperorObjective();
        assertEquals(1f, emperorObjective.getCompletion(null, null));
    }

    @Test
    @DisplayName("Copy")
    void copy() {
        EmperorObjective emperorObjective = new EmperorObjective();
        EmperorObjective copy = (EmperorObjective) emperorObjective.copy();
        assertEquals(emperorObjective.getPoints(), copy.getPoints());
        assertEquals(emperorObjective.getType(), copy.getType());
        assertEquals(emperorObjective.getState(), copy.getState());
    }

    @Test
    @DisplayName("Reset")
    void reset() {
        EmperorObjective emperorObjective = new EmperorObjective();
        emperorObjective.reset();
        assertTrue(emperorObjective.isAchieved());
    }

    @Test
    @DisplayName("Verify")
    void verify() {
        EmperorObjective emperorObjective = new EmperorObjective();
        emperorObjective.verify(null, null);
        assertTrue(emperorObjective.isAchieved());
    }
}

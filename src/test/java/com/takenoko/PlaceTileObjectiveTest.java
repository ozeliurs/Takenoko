package com.takenoko;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaceTileObjectiveTest {

    private PlaceTileObjective placeTileObjective;

    @BeforeEach
    void setup() {
        placeTileObjective = new PlaceTileObjective(1);
    }

    @Test
    void setAction() {
        assertFalse(placeTileObjective.getCounterGoal() == 3);
        placeTileObjective.setAction(3);
        assertTrue(placeTileObjective.getCounterGoal() == 3);
    }

    @Test
    void incrementCounter() {
        assertTrue(placeTileObjective.getCounter() == 0);
        placeTileObjective.incrementCounter();
        assertTrue(placeTileObjective.getCounter() == 1);
    }

    @Test
    void isAchieved() {
        assertFalse(placeTileObjective.isAchieved());
        placeTileObjective.incrementCounter();
        assertTrue(placeTileObjective.isAchieved());
    }
}

package com.takenoko.asset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IrrigationDeckTest {
    @Test
    @DisplayName("method draw")
    void draw() {
        IrrigationDeck irrigationDeck = new IrrigationDeck();
        irrigationDeck.draw();
        assertThat(irrigationDeck.getSize()).isEqualTo(19);
    }

    @Test
    @DisplayName("method hasIrrigation")
    void hasIrrigation() {
        IrrigationDeck irrigationDeck = new IrrigationDeck();
        assertThat(irrigationDeck.hasIrrigation()).isTrue();
    }

    @Test
    @DisplayName("When irrigationDeck-s are equal, returns true")
    void equals() {
        IrrigationDeck irrigationDeck1 = new IrrigationDeck();
        IrrigationDeck irrigationDeck2 = new IrrigationDeck();
        assertThat(irrigationDeck1).isEqualTo(irrigationDeck2);
    }

    @Test
    @DisplayName("When irrigationDeck-s are equal, returns same hash code")
    void hashCodeTest() {
        IrrigationDeck irrigationDeck1 = new IrrigationDeck();
        IrrigationDeck irrigationDeck2 = new IrrigationDeck();
        assertThat(irrigationDeck1).hasSameHashCodeAs(irrigationDeck2);
    }
}

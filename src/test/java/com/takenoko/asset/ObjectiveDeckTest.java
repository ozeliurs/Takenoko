package com.takenoko.asset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ObjectiveDeckTest {

    @DisplayName("constructor")
    @Test
    void constructor() {
        // Given
        ObjectiveDeck objectiveDeck = new ObjectiveDeck();

        // Then
        assertThat(objectiveDeck).hasSize(45);
    }
}

package com.takenoko.asset;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.objective.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("getStarterDeck")
    class TestGetStarterDeck {
        @Test
        @DisplayName("should return a deck of 3 objectives with 1 of each type")
        void getStarterDeck() {
            List<Objective> starterDeck = (new ObjectiveDeck()).getStarterDeck();
            assertThat(starterDeck)
                    .hasSize(3)
                    .hasOnlyElementsOfTypes(
                            PandaObjective.class,
                            PatternObjective.class,
                            SingleGardenerObjective.class,
                            MultipleGardenerObjective.class);
        }
    }
}

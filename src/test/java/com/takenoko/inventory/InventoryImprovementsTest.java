package com.takenoko.inventory;

import static org.assertj.core.api.Assertions.*;

import com.takenoko.layers.tile.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InventoryImprovementsTest {

    private InventoryImprovements inventoryImprovements;

    @BeforeEach
    void setUp() {
        inventoryImprovements = new InventoryImprovements();
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the object")
        void copy_shouldReturnCopyOfObject() {
            InventoryImprovements copy = inventoryImprovements.copy();
            assertThat(copy).isEqualTo(inventoryImprovements);
        }
    }

    @Nested
    @DisplayName("Method store()")
    class TestStore {
        @Test
        @DisplayName("should store the improvement")
        void store_shouldStoreTheImprovement() {
            assertThat(inventoryImprovements).doesNotContain(ImprovementType.FERTILIZER);
            inventoryImprovements.store(ImprovementType.FERTILIZER);
            assertThat(inventoryImprovements).contains(ImprovementType.FERTILIZER);
        }
    }

    @Nested
    @DisplayName("Method use()")
    class TestUse {
        @Test
        @DisplayName("should remove the improvement from list if in")
        void use_shouldRemoveTheImprovementFromListIfIn() {
            inventoryImprovements.store(ImprovementType.FERTILIZER);
            inventoryImprovements.use(ImprovementType.FERTILIZER);
            assertThat(inventoryImprovements).doesNotContain(ImprovementType.FERTILIZER);
        }

        @Test
        @DisplayName("should throw exception if not in list")
        void use_shouldThrowExceptionIfNotInList() {
            assertThatThrownBy(() -> inventoryImprovements.use(ImprovementType.FERTILIZER))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("You do not possess any improvements of type: FERTILIZER");
        }
    }
}

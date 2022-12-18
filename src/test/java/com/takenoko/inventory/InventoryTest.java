package com.takenoko.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryTest {

    @Test
    @DisplayName("should return the bamboo stack")
    void shouldReturnTheBambooStack() {
        InventoryBambooStack bambooStack = new InventoryBambooStack(5);
        Inventory inventory = new Inventory(bambooStack);
        assertThat(inventory.getBambooStack()).isEqualTo(bambooStack);
    }
}

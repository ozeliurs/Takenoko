package com.takenoko.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.TileColor;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    @DisplayName("should return the bamboo stack")
    void shouldReturnTheBambooStack() {
        EnumMap<TileColor, InventoryBambooStack> bambooStacks = new EnumMap<>(TileColor.class);
        bambooStacks.put(TileColor.GREEN, new InventoryBambooStack(5));
        bambooStacks.put(TileColor.YELLOW, new InventoryBambooStack(5));
        bambooStacks.put(TileColor.PINK, new InventoryBambooStack(5));
        Inventory inventory = new Inventory(bambooStacks);
        assertThat(inventory.getBambooStack(TileColor.GREEN))
                .isEqualTo(bambooStacks.get(TileColor.GREEN));
        assertThat(inventory.getBambooStack(TileColor.YELLOW))
                .isEqualTo(bambooStacks.get(TileColor.YELLOW));
        assertThat(inventory.getBambooStack(TileColor.PINK))
                .isEqualTo(bambooStacks.get(TileColor.PINK));
    }

    @Test
    @DisplayName("should return the improvements")
    void shouldReturnTheImprovements() {
        EnumMap<TileColor, InventoryBambooStack> inventoryBambooStack =
                new EnumMap<>(TileColor.class);
        InventoryImprovements inventoryImprovements =
                new InventoryImprovements(List.of(ImprovementType.values()));
        Inventory inventory = new Inventory(inventoryBambooStack, inventoryImprovements);
        assertThat(inventory.getInventoryImprovements()).isEqualTo(inventoryImprovements);
    }

    @Test
    @DisplayName("should clear both bambooStack and improvements")
    void shouldClearBothBambooStackAndImprovements() {
        EnumMap<TileColor, InventoryBambooStack> inventoryBambooStack =
                new EnumMap<>(TileColor.class);
        InventoryImprovements inventoryImprovements =
                new InventoryImprovements(List.of(ImprovementType.values()));
        Inventory inventory = new Inventory(inventoryBambooStack, inventoryImprovements);
        inventory.clear();
        assertTrue(inventory.getBambooStack(TileColor.ANY).isEmpty());
        assertThat(inventory.getInventoryImprovements()).isEmpty();
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the two objects are the same")
        void equals_shouldReturnTrueWhenSameObject() {
            assertThat(inventory).isEqualTo(inventory);
        }

        @Test
        @DisplayName("should return true when the two objects are equal")
        void equals_shouldReturnTrueWhenEqual() {
            Inventory other = new Inventory();
            assertThat(inventory).isEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the two objects are not equal")
        void equals_shouldReturnFalseWhenNotEqual() {
            Inventory other = new Inventory();
            other.getBambooStack(TileColor.GREEN).collectBamboo();
            assertThat(inventory).isNotEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the other object is null")
        void equals_shouldReturnFalseWhenOtherIsNull() {
            assertThat(inventory).isNotEqualTo(null);
        }

        @Test
        @DisplayName("should return false when the other object is not a Inventory")
        void equals_shouldReturnFalseWhenOtherIsNotInventory() {
            assertThat(inventory).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the two objects are equal")
        void hashCode_shouldReturnSameHashCodeWhenEqual() {
            Inventory other = new Inventory();
            assertThat(inventory).hasSameHashCodeAs(other);
        }

        @Test
        @DisplayName("should return a different hash code when the two objects are not equal")
        void hashCode_shouldReturnDifferentHashCodeWhenNotEqual() {
            Inventory other = new Inventory();
            other.getBambooStack(TileColor.GREEN).collectBamboo();
            assertThat(inventory).doesNotHaveSameHashCodeAs(other);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the object")
        void copy_shouldReturnCopyOfObject() {
            Inventory copy = inventory.copy();
            assertThat(copy).isEqualTo(inventory).isNotSameAs(inventory);
        }
    }

    @Nested
    @DisplayName("Method storeImprovement()")
    class TestStoreImprovement {
        @Test
        @DisplayName("should call method store in InventoryImprovements")
        void storeImprovement_shouldCallMethodStoreInInventoryImprovements() {
            InventoryImprovements inventoryImprovements = mock(InventoryImprovements.class);
            EnumMap<TileColor, InventoryBambooStack> inventoryBambooStack =
                    new EnumMap<>(TileColor.class);
            inventory = new Inventory(inventoryBambooStack, inventoryImprovements);
            inventory.storeImprovement(ImprovementType.FERTILIZER);
            verify(inventoryImprovements, times(1)).store(ImprovementType.FERTILIZER);
        }
    }

    @Nested
    @DisplayName("Method useImprovement()")
    class TestUseImprovement {
        @Test
        @DisplayName("should call method use in InventoryImprovements")
        void useImprovement_shouldCallMethodUseInInventoryImprovements() {
            InventoryImprovements inventoryImprovements = mock(InventoryImprovements.class);
            EnumMap<TileColor, InventoryBambooStack> inventoryBambooStack = mock(EnumMap.class);
            inventory = new Inventory(inventoryBambooStack, inventoryImprovements);
            inventory.useImprovement(ImprovementType.FERTILIZER);
            verify(inventoryImprovements, times(1)).use(ImprovementType.FERTILIZER);
        }
    }

    @Nested
    @DisplayName("Method hasImprovement()")
    class TestHasImprovement {
        @Test
        @DisplayName("should call method hasImprovement in InventoryImprovements")
        void hasImprovement_shouldCallMethodHasImprovementInInventoryImprovements() {
            InventoryImprovements inventoryImprovements = mock(InventoryImprovements.class);
            EnumMap<TileColor, InventoryBambooStack> inventoryBambooStack =
                    new EnumMap<>(TileColor.class);
            when(inventoryImprovements.hasImprovement(ImprovementType.FERTILIZER)).thenReturn(true);
            inventory = new Inventory(inventoryBambooStack, inventoryImprovements);
            assertThat(inventory.hasImprovement(ImprovementType.FERTILIZER)).isTrue();
            verify(inventoryImprovements, times(1)).hasImprovement(ImprovementType.FERTILIZER);

            when(inventoryImprovements.hasImprovement(ImprovementType.FERTILIZER))
                    .thenReturn(false);
            assertThat(inventory.hasImprovement(ImprovementType.FERTILIZER)).isFalse();
            verify(inventoryImprovements, times(2)).hasImprovement(ImprovementType.FERTILIZER);
        }
    }
}

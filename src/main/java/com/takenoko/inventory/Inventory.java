package com.takenoko.inventory;

import com.takenoko.layers.tile.ImprovementType;
import java.util.Objects;

/** Inventory contains elements stored by player */
public class Inventory {
    private final InventoryBambooStack bambooStack;
    private final InventoryImprovements inventoryImprovements;

    public Inventory(
            InventoryBambooStack bambooStack, InventoryImprovements inventoryImprovements) {
        this.bambooStack = bambooStack;
        this.inventoryImprovements = inventoryImprovements;
    }

    public Inventory(InventoryBambooStack bambooStack) {
        this(bambooStack, new InventoryImprovements());
    }

    public Inventory() {
        this(new InventoryBambooStack(0), new InventoryImprovements());
    }

    /** add 1 bamboo to the inventory */
    public void collectBamboo() {
        getBambooStack().collectBamboo();
    }

    /**
     * @param improvementType store an improvement in order to use it later on
     */
    public void storeImprovement(ImprovementType improvementType) {
        getInventoryImprovements().store(improvementType);
    }

    /**
     * @param improvementType use improvement previously stored
     */
    public void useImprovement(ImprovementType improvementType) {
        getInventoryImprovements().use(improvementType);
    }

    /**
     * @return number of bamboos in Inventory
     */
    public int getBambooCount() {
        return getBambooStack().getBambooCount();
    }

    /**
     * @return the bambooStack
     */
    public InventoryBambooStack getBambooStack() {
        return bambooStack;
    }

    /**
     * @return the list of stored improvements
     */
    public InventoryImprovements getInventoryImprovements() {
        return inventoryImprovements;
    }

    /** reset inventory */
    public void clear() {
        bambooStack.clear();
        inventoryImprovements.clear();
    }

    public Inventory copy() {
        return new Inventory(bambooStack.copy(), inventoryImprovements.copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return getBambooStack().equals(inventory.getBambooStack())
                && inventoryImprovements.equals(inventory.inventoryImprovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBambooStack(), inventoryImprovements);
    }
}

package com.takenoko.inventory;

import com.takenoko.layers.tile.ImprovementType;

import java.util.Objects;

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

    public void collectBamboo(){
        bambooStack.collectBamboo();
    }

    public void storeImprovement(ImprovementType improvementType){
        inventoryImprovements.store(improvementType);
    }

    public void useImprovement(ImprovementType improvementType){
        inventoryImprovements.use(improvementType);
    }

    public int getBambooCount(){
        return bambooStack.getBambooCount();
    }

    public InventoryBambooStack getBambooStack() {
        return bambooStack;
    }

    public InventoryImprovements getInventoryImprovements() {
        return inventoryImprovements;
    }

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

package com.takenoko.inventory;

public class Inventory {
    private final InventoryBambooStack bambooStack;

    public Inventory(InventoryBambooStack bambooStack) {
        this.bambooStack = bambooStack;
    }

    public Inventory() {
        this(new InventoryBambooStack(0));
    }

    public InventoryBambooStack getBambooStack() {
        return bambooStack;
    }
}

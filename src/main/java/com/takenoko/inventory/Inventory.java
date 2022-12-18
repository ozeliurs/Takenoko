package com.takenoko.inventory;

public class Inventory {
    private final InventoryBambooStack bambooStack;

    public Inventory() {
        bambooStack = new InventoryBambooStack(0);
        bambooStack.addBamboo();
    }

    public InventoryBambooStack getBambooStack() {
        return bambooStack;
    }
}

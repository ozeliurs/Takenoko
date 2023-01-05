package com.takenoko.inventory;

import java.util.Objects;

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

    public void clear() {
        bambooStack.clear();
    }

    public Inventory copy() {
        return new Inventory(bambooStack.copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return getBambooStack().equals(inventory.getBambooStack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBambooStack());
    }
}

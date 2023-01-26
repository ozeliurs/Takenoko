package com.takenoko.inventory;

import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.TileColor;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Inventory contains elements stored by player */
public class Inventory {
    private final Map<TileColor, InventoryBambooStack> bambooStacks;
    private final InventoryImprovements inventoryImprovements;

    public Inventory(Map<TileColor, InventoryBambooStack> bambooStacks, InventoryImprovements inventoryImprovements) {
        this.bambooStacks = bambooStacks;
        this.inventoryImprovements = inventoryImprovements;
    }

    public Inventory(InventoryBambooStack bambooStack) {
        this(bambooStack, new InventoryImprovements());
    }

    public Inventory() {
        this(new InventoryBambooStack(0), new InventoryImprovements());
    }

    public Inventory(Inventory inventory) {
        this.bambooStacks = new EnumMap<>(TileColor.class);
        for (TileColor tileColor : TileColor.values()) {
            this.bambooStacks.put(tileColor, inventory.getBambooStack(tileColor).copy());
        }
        this.inventoryImprovements = inventory.getInventoryImprovements().copy();
    }

    /** add 1 bamboo to the inventory */
    public void collectBamboo(TileColor tileColor) {
        getBambooStack(tileColor).collectBamboo();
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
        return getBambooStack(TileColor.ANY).getBambooCount();
    }

    /**
     * @return the bambooStack
     */
    public InventoryBambooStack getBambooStack(TileColor tileColor) {
        return bambooStacks.get(tileColor);
    }

    /**
     * @return the list of stored improvements
     */
    public InventoryImprovements getInventoryImprovements() {
        return inventoryImprovements;
    }

    /** reset inventory */
    public void clear() {
        bambooStacks.clear();
        inventoryImprovements.clear();
    }

    public Inventory copy() {
        return new Inventory(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return getBambooStack(TileColor.ANY).equals(inventory.getBambooStack(TileColor.ANY))
                && inventoryImprovements.equals(inventory.inventoryImprovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBambooStack(TileColor.ANY), inventoryImprovements);
    }

    public boolean hasImprovement(ImprovementType improvementType) {
        return inventoryImprovements.hasImprovement(improvementType);
    }
}

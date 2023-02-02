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

    /**
     * Constructor
     *
     * @param bambooStacks bamboo stacks
     * @param inventoryImprovements improvements
     */
    public Inventory(
            Map<TileColor, InventoryBambooStack> bambooStacks,
            InventoryImprovements inventoryImprovements) {
        this.bambooStacks = bambooStacks;
        this.inventoryImprovements = inventoryImprovements;
    }

    /**
     * Constructor
     *
     * @param bambooStacks bamboo stacks
     */
    public Inventory(Map<TileColor, InventoryBambooStack> bambooStacks) {
        this(bambooStacks, new InventoryImprovements());
    }

    /** Constructor */
    public Inventory() {
        this(new EnumMap<>(TileColor.class), new InventoryImprovements());
    }

    /**
     * Constructor
     *
     * @param inventory inventory
     */
    public Inventory(Inventory inventory) {
        this.bambooStacks = new EnumMap<>(TileColor.class);

        this.bambooStacks.put(TileColor.GREEN, inventory.getBambooStack(TileColor.GREEN).copy());
        this.bambooStacks.put(TileColor.YELLOW, inventory.getBambooStack(TileColor.YELLOW).copy());
        this.bambooStacks.put(TileColor.PINK, inventory.getBambooStack(TileColor.PINK).copy());

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
        return getBambooCount(TileColor.GREEN)
                + getBambooCount(TileColor.YELLOW)
                + getBambooCount(TileColor.PINK);
    }

    /**
     * Return the number of bamboo of a specific color
     *
     * @param tileColor color of bamboo
     * @return number of bamboos in Inventory
     */
    public int getBambooCount(TileColor tileColor) {
        return getBambooStack(tileColor).getBambooCount();
    }

    /**
     * @return the bambooStack
     */
    public InventoryBambooStack getBambooStack(TileColor tileColor) {
        bambooStacks.computeIfAbsent(tileColor, k -> new InventoryBambooStack(0));
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
        return Objects.equals(bambooStacks, inventory.bambooStacks)
                && inventoryImprovements.equals(inventory.inventoryImprovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bambooStacks, inventoryImprovements);
    }

    /**
     * Method to verify whether the inventory contains the improvement type specified or not
     *
     * @param improvementType improvement type
     */
    public boolean hasImprovement(ImprovementType improvementType) {
        return inventoryImprovements.hasImprovement(improvementType);
    }

    /**
     * Method to verify whether the inventory contains any improvement or not
     *
     * @return true if the inventory contains any improvement, false otherwise
     */
    public boolean hasImprovement() {
        return !inventoryImprovements.isEmpty();
    }

    /**
     * Remove bamboo from the player's inventory when an objective is redeemed
     *
     * @param bambooTarget how many bamboo to use
     */
    public void useBamboo(Map<TileColor, Integer> bambooTarget) {
        bambooTarget.forEach((tileColor, count) -> getBambooStack(tileColor).useBamboo(count));
    }

    @Override
    public String toString() {
        return "Inventory{"
                + "bambooStacks="
                + bambooStacks
                + ", inventoryImprovements="
                + inventoryImprovements
                + '}';
    }
}

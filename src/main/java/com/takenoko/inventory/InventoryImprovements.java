package com.takenoko.inventory;

import com.takenoko.layers.tile.ImprovementType;
import java.util.ArrayList;
import java.util.List;

/**
 * InventoryImprovements contains the improvements picked by the player after getting Cloudy weather
 * InventoryImprovements is an attribute in Inventory
 */
public class InventoryImprovements extends ArrayList<ImprovementType> {
    public InventoryImprovements() {
        super();
    }

    public InventoryImprovements(List<ImprovementType> inventoryImprovements) {
        this.addAll(inventoryImprovements);
    }

    /**
     * @return copy of the stash of improvement stored by the bot
     */
    public InventoryImprovements copy() {
        return new InventoryImprovements(this);
    }

    /**
     * @param pickedImprovement store the chosen improvement in the inventory
     */
    public void store(ImprovementType pickedImprovement) {
        super.add(pickedImprovement);
    }

    /**
     * @param toUse remove used improvement from list or throw exception if not possessed
     */
    public void use(ImprovementType toUse) {
        if (!contains(toUse)) {
            throw new IllegalArgumentException(
                    "You do not possess any improvements of type: " + toUse);
        }
        super.remove(toUse);
    }
}

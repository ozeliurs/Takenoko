package com.takenoko.inventory;

import com.takenoko.layers.bamboo.BambooStack;

/** InventoryBambooStack represents the bamboos collected by the player */
public class InventoryBambooStack extends BambooStack {

    public InventoryBambooStack(int startingBamboo) {
        super(startingBamboo);
    }

    /** add 1 bamboo to inventory */
    public void collectBamboo() {
        growBamboo();
    }

    public InventoryBambooStack(InventoryBambooStack bambooStack) {
        super(bambooStack.getBambooCount());
    }

    @Override
    public InventoryBambooStack copy() {
        return new InventoryBambooStack(this);
    }

    @Override
    public String toString() {
        return "InventoryBambooStack{" + "bambooCount=" + bambooCount + '}';
    }
}

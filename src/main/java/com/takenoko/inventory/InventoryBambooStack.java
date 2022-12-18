package com.takenoko.inventory;

import com.takenoko.layers.bamboo.BambooStack;

public class InventoryBambooStack extends BambooStack {

    public InventoryBambooStack(int startingBamboo) {
        super(startingBamboo);
    }

    @Override
    protected void addBamboo() {
        bambooCount++;
    }

    @Override
    protected void subBamboo() {
        if (bambooCount == 0) {
            throw new IllegalArgumentException("There is no bamboo to remove");
        }
        bambooCount--;
    }
}

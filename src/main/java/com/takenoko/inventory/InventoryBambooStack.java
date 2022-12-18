package com.takenoko.inventory;

import com.takenoko.layers.bamboo.BambooStack;

public class InventoryBambooStack extends BambooStack {

    public InventoryBambooStack(int startingBamboo) {
        super(startingBamboo);
    }

    protected void collectBamboo() {
        growBamboo();
    }
}

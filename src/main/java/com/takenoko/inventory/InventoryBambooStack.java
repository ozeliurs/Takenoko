package com.takenoko.inventory;

import com.takenoko.layers.bamboo.BambooStack;

public class InventoryBambooStack extends BambooStack {

    public InventoryBambooStack(int startingBamboo) {
        super(startingBamboo);
    }

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
}

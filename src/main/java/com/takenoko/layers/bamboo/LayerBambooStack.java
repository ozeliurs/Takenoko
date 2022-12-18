package com.takenoko.layers.bamboo;

public class LayerBambooStack extends BambooStack {
    private static final int MAX_BAMBOO = 4;

    public LayerBambooStack(int startingBamboo) {
        super(startingBamboo);
    }

    @Override
    protected void growBamboo() {
        if (bambooCount == MAX_BAMBOO) {
            throw new IllegalArgumentException("There is no more room for bamboo");
        }
        bambooCount++;
    }

    @Override
    protected void eatBamboo() {
        if (bambooCount == 0) {
            throw new IllegalArgumentException("There is no bamboo to remove");
        }
        bambooCount--;
    }
}

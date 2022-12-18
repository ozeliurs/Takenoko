package com.takenoko.layers.bamboo;

import java.util.Objects;

public class BambooStack {
    protected int bambooCount;

    public BambooStack(int startingBamboo) {
        bambooCount = startingBamboo;
    }

    protected void addBamboo() {
        bambooCount++;
    }

    protected void subBamboo() {
        if (bambooCount == 0) {
            throw new IllegalArgumentException("There is no bamboo to remove");
        }
        bambooCount--;
    }

    public int getBambooCount() {
        return bambooCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayerBambooStack that = (LayerBambooStack) o;
        return getBambooCount() == that.getBambooCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBambooCount());
    }
}

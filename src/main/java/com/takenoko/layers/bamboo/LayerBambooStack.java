package com.takenoko.layers.bamboo;

import java.util.Objects;

public class LayerBambooStack extends BambooStack {
    private static final int MAX_BAMBOO = 4;
    private boolean isImmutable = false;

    public LayerBambooStack(int startingBamboo) {
        super(startingBamboo);
    }

    public LayerBambooStack(int startingBamboo, boolean isImmutable) {
        super(startingBamboo);
        this.isImmutable = isImmutable;
    }

    @Override
    protected void growBamboo() {
        if (bambooCount >= MAX_BAMBOO) {
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

    /*
     * @return if the bamboo can grow
     */
    public boolean isGrowable() {
        return bambooCount < MAX_BAMBOO && !isImmutable;
    }

    /*
     * @return if the bamboo can be eaten
     */
    public boolean isEatable() {
        return bambooCount > 0 && !isImmutable;
    }

    @Override
    public LayerBambooStack copy() {
        return new LayerBambooStack(this.getBambooCount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LayerBambooStack that = (LayerBambooStack) o;
        return isImmutable == that.isImmutable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isImmutable);
    }
}

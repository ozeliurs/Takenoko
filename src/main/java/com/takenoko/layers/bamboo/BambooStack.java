package com.takenoko.layers.bamboo;

import java.util.Objects;

public class BambooStack {
    protected int bambooCount;

    public BambooStack(int startingBamboo) {
        bambooCount = startingBamboo;
    }

    public BambooStack(BambooStack bambooStack) {
        this(bambooStack.bambooCount);
    }

    /** increment bambooCount by 1 */
    protected void growBamboo() {
        bambooCount++;
    }

    /** lower bambooCount by 1 */
    protected void eatBamboo() {
        if (bambooCount == 0) {
            throw new IllegalArgumentException("There is no bamboo to remove");
        }
        bambooCount--;
    }

    /**
     * @return number of bamboos
     */
    public int getBambooCount() {
        return bambooCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BambooStack that = (BambooStack) o;
        return getBambooCount() == that.getBambooCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBambooCount());
    }

    public BambooStack copy() {
        return new BambooStack(this);
    }

    /**
     * @return true if bambooCount is 0
     */
    public boolean isEmpty() {
        return bambooCount == 0;
    }

    /** reset bamboo counter to value 0 */
    public void clear() {
        bambooCount = 0;
    }
}

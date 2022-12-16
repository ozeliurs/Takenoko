package com.takenoko.layers.bamboo;

import java.util.Objects;

public class BambooStack {
    private int bamboo;

    public BambooStack(int startingBamboo) {
        bamboo = startingBamboo;
    }

    public BambooStack() {
        this(0);
    }

    void addBamboo() {
        bamboo++;
    }

    void subBamboo() {
        bamboo--;
    }

    public int getBamboo() {
        return bamboo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BambooStack that = (BambooStack) o;
        return getBamboo() == that.getBamboo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBamboo());
    }
}

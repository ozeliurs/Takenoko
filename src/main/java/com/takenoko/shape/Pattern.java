package com.takenoko.shape;

import java.util.Objects;
import java.util.stream.Stream;

public class Pattern extends Shape {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pattern pattern = (Pattern) o;

        return getRotatedShapes().equals(pattern.getRotatedShapes());
    }

    @Override
    public int hashCode() {
        return shape != null ? shape.hashCode() : 0;
    }
}

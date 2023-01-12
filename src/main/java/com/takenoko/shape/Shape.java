package com.takenoko.shape;

import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

/** Class representing a Shape. */
public class Shape {
    private final Set<PositionVector> elements;
    private final PositionVector origin;

    /**
     * Constructor for the Shape class.
     *
     * @param shape the pattern of the shape
     * @param origin the origin of the shape
     */
    public Shape(Set<PositionVector> shape, PositionVector origin) {
        if (shape.isEmpty()) {
            throw new IllegalArgumentException("The shape cannot be empty");
        }
        this.elements = shape;
        this.origin = origin;
    }

    /**
     * Constructor for the Shape class. The origin is the element the closest to the origin of the
     * coordinate system.
     *
     * @param shape the pattern of the shape
     */
    public Shape(Set<PositionVector> shape) {
        this(shape, findOrigin(shape));
    }

    /**
     * Find the origin of the shape.
     *
     * @param shape the pattern of the shape
     * @return the origin of the shape
     */
    private static PositionVector findOrigin(Set<PositionVector> shape) {
        return shape.stream()
                .min(
                        (v1, v2) -> {
                            double v1Distance = v1.distance(new PositionVector(0, 0, 0));
                            double v2Distance = v2.distance(new PositionVector(0, 0, 0));
                            return Double.compare(v1Distance, v2Distance);
                        })
                .orElse(new PositionVector(0, 0, 0));
    }

    /**
     * Constructor of the Shape class. To facilitate the creation of the shape, the shape is defined
     * by a list of vectors. The origin is the element the closest to the origin of the coordinate
     *
     * @param vectors the vectors of the shape
     */
    public Shape(PositionVector... vectors) {
        this(new HashSet<>(Arrays.asList(vectors)));
    }

    public Set<PositionVector> getElements() {
        return new HashSet<>(this.elements);
    }

    /**
     * Returns a new shape with the same pattern but rotated 60 degrees around the given origin. see
     * <a
     * href="https://www.redblobgames.com/grids/hexagons/#rotation">https://www.redblobgames.com/grids/hexagons/#rotation</a>
     *
     * @param origin pivot point of the rotation
     * @return the rotated shape
     */
    public Shape rotate60(PositionVector origin) {
        return new Shape(
                this.elements.stream()
                        .map(v -> v.sub(origin).rotate60().add(origin))
                        .map(PositionVector::new)
                        .collect(HashSet::new, HashSet::add, HashSet::addAll),
                origin);
    }

    /**
     * Returns a new shape with the same pattern but rotated 60 degrees around the origin of the
     * shape.
     *
     * @return the shape rotated 60 degrees around the first element of the pattern
     */
    public Shape rotate60() {
        return this.rotate60(this.origin);
    }

    public Set<Shape> getRotatedShapes() {
        // return a list of shapes rotated in all directions
        return IntStream.range(0, 5)
                .mapToObj(
                        i -> {
                            Shape rotatedShape = this.copy();
                            for (int j = 0; j < i; j++) {
                                rotatedShape = rotatedShape.rotate60();
                            }
                            return rotatedShape;
                        })
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    /**
     * Returns a new shape with the same pattern but translated by the given vector.
     *
     * @param vector the vector to translate the shape by
     * @return a new shape with the same pattern but translated by the given vector
     */
    public Shape translate(PositionVector vector) {
        return new Shape(
                this.elements.stream()
                        .map(v -> v.add(vector))
                        .map(Vector::toPositionVector)
                        .collect(HashSet::new, HashSet::add, HashSet::addAll),
                this.origin.add(vector).toPositionVector());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return getElements().equals(shape.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElements());
    }

    public Shape copy() {
        return new Shape(this.elements, this.origin);
    }

    @Override
    public String toString() {
        return "Shape{" + "pattern=" + elements + ", origin=" + origin + '}';
    }
}

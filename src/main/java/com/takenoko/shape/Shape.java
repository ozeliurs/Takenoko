package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.*;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.Pair;

/** Class representing a Shape. */
public class Shape {
    public static final IllegalArgumentException THE_SHAPE_CANNOT_BE_EMPTY_EXCEPTION =
            new IllegalArgumentException("The shape cannot be empty");
    private final Map<PositionVector, Tile> elements;
    private final PositionVector defaultRotationOrigin;

    /**
     * Constructor for the Shape class.
     *
     * @param shape the pattern of the shape
     * @param defaultRotationOrigin the default rotation origin of the shape
     */
    public Shape(Map<PositionVector, Tile> shape, PositionVector defaultRotationOrigin) {
        if (shape.isEmpty()) {
            throw THE_SHAPE_CANNOT_BE_EMPTY_EXCEPTION;
        }
        this.elements = shape;
        this.defaultRotationOrigin = defaultRotationOrigin;
    }

    /**
     * Constructor for the Shape class. The rotation origin is the element the closest to the origin
     * of the coordinate system.
     *
     * @param shape the pattern of the shape
     */
    public Shape(Map<PositionVector, Tile> shape) {
        this(shape, findOrigin(shape));
    }

    /**
     * Find the rotation origin of the shape.
     *
     * @param shape the pattern of the shape
     * @return the rotation origin of the shape
     */
    private static PositionVector findOrigin(Map<PositionVector, Tile> shape) {
        return shape.keySet().stream()
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
     * by a list of vectors. The rotation origin is the element the closest to the origin of the
     * coordinate
     *
     * @param vectors the vectors of the shape
     */
    public Shape(PositionVector... vectors) {
        if (vectors.length == 0) {
            throw THE_SHAPE_CANNOT_BE_EMPTY_EXCEPTION;
        }
        this.elements = new HashMap<>();
        this.defaultRotationOrigin = findOrigin(this.elements);
        for (PositionVector vector : vectors) {
            this.elements.put(vector, new Tile());
        }
    }

    public Shape(Shape shape) {
        this.elements = new HashMap<>(shape.elements);
        this.defaultRotationOrigin = shape.defaultRotationOrigin;
    }

    /**
     * Constructor of the Shape class. To facilitate the creation of the shape, the shape is defined
     * by a list of Pair of vectors and tiles. The rotation origin is the element the closest to the
     * origin of the coordinate system.
     */
    @SafeVarargs
    public Shape(Pair<PositionVector, Tile>... vectors) {
        if (vectors.length == 0) {
            throw THE_SHAPE_CANNOT_BE_EMPTY_EXCEPTION;
        }
        this.elements = new HashMap<>();
        this.defaultRotationOrigin = findOrigin(this.elements);
        for (Pair<PositionVector, Tile> vector : vectors) {
            this.elements.put(vector.getLeft(), vector.getRight());
        }
    }

    public Map<PositionVector, Tile> getElements() {
        return this.elements;
    }

    /**
     * Returns a new shape with the same pattern but rotated 60 degrees around the given rotation
     * rotationOrigin. see <a
     * href="https://www.redblobgames.com/grids/hexagons/#rotation">https://www.redblobgames.com/grids/hexagons/#rotation</a>
     *
     * @param rotationOrigin pivot point of the rotation
     * @return the rotated shape
     */
    public Shape rotate60(PositionVector rotationOrigin) {
        return new Shape(
                this.elements.entrySet().stream()
                        .map(
                                v ->
                                        Pair.of(
                                                v.getKey()
                                                        .sub(rotationOrigin)
                                                        .rotate60()
                                                        .add(rotationOrigin)
                                                        .toPositionVector(),
                                                v.getValue()))
                        .collect(
                                HashMap::new,
                                (m, v) -> m.put(v.getLeft(), v.getRight()),
                                HashMap::putAll),
                rotationOrigin);
    }

    /**
     * Returns a new shape with the same pattern but rotated 60 degrees around the rotation origin
     * of the shape.
     *
     * @return the shape rotated 60 degrees around the first element of the pattern
     */
    public Shape rotate60() {
        return this.rotate60(this.defaultRotationOrigin);
    }

    public Set<Shape> getRotatedShapes() {
        // return a list of shapes rotated in all directions
        return IntStream.range(0, 6) // From 0 to 5
                .mapToObj(this::getRotatedShape)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    public Shape getRotatedShape(int i) {
        Shape rotatedShape = this.copy();
        for (int j = 0; j < i; j++) {
            rotatedShape = rotatedShape.rotate60();
        }
        return rotatedShape;
    }

    /**
     * Returns a new shape with the same pattern but translated by the given vector.
     *
     * @param vector the vector to translate the shape by
     * @return a new shape with the same pattern but translated by the given vector
     */
    public Shape translate(PositionVector vector) {
        return new Shape(
                this.elements.entrySet().stream()
                        .map(v -> Pair.of(v.getKey().add(vector).toPositionVector(), v.getValue()))
                        .collect(
                                HashMap::new,
                                (m, v) -> m.put(v.getLeft(), v.getRight()),
                                HashMap::putAll),
                this.defaultRotationOrigin.add(vector).toPositionVector());
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
        return new Shape(this.elements, this.defaultRotationOrigin);
    }

    @Override
    public String toString() {
        return "Shape{" + "pattern=" + elements + ", rotationOrigin=" + defaultRotationOrigin + '}';
    }

    public Shape getMissingShape(Shape other) {
        return new Shape(
                this.elements.entrySet().stream()
                        .filter(e -> !other.elements.containsKey(e.getKey()))
                        .collect(
                                HashMap::new,
                                (m, v) -> m.put(v.getKey(), v.getValue()),
                                HashMap::putAll),
                this.defaultRotationOrigin);
    }
}

package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.Direction;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.*;

public class Shape {
    private final Set<PositionVector> pattern;
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
        this.pattern = shape;
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

    public Set<PositionVector> getPattern() {
        return new HashSet<>(this.pattern);
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
                this.pattern.stream()
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

    /**
     * Returns a new shape with the same pattern but translated by the given vector.
     *
     * @param vector the vector to translate the shape by
     * @return a new shape with the same pattern but translated by the given vector
     */
    public Shape translate(PositionVector vector) {
        return new Shape(
                this.pattern.stream()
                        .map(v -> v.add(vector))
                        .map(Vector::toPositionVector)
                        .collect(HashSet::new, HashSet::add, HashSet::addAll),
                this.origin.add(vector).toPositionVector());
    }

    /**
     * Method to match a shape on the board.
     *
     * @param tileMap the tileMap to match the shape on
     * @return the matching translated/rotated shapes
     */
    public List<Shape> match(Map<PositionVector, Tile> tileMap) {
        HashSet<Shape> matches = new HashSet<>();

        for (PositionVector tilePosition : tileMap.keySet()) {
            // For each tilePosition on the board translate the shape to the tilePosition
            Shape translatedShape = this.translate(tilePosition);
            for (int i = 0; i < Direction.values().length; i++) {
                // Check if the translated shape matches the board
                boolean fullMatch =
                        translatedShape.getPattern().stream().allMatch(tileMap::containsKey);

                if (fullMatch) {
                    matches.add(translatedShape);
                }
                translatedShape = translatedShape.rotate60();
            }
        }
        return matches.stream().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return getPattern().equals(shape.getPattern());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPattern());
    }

    @Override
    public String toString() {
        return "Shape{" + "pattern=" + pattern + ", origin=" + origin + '}';
    }
}

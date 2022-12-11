package com.takenoko.shape;

import com.takenoko.Board;
import com.takenoko.vector.Direction;
import com.takenoko.vector.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Shape {
    private final Set<Vector> pattern;
    private final Vector origin;

    /**
     * Constructor for the Shape class.
     *
     * @param shape the pattern of the shape
     * @param origin the origin of the shape
     */
    public Shape(Set<Vector> shape, Vector origin) {
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
    public Shape(Set<Vector> shape) {
        this(
                shape,
                shape.stream()
                        .min(
                                (v1, v2) -> {
                                    float v1Distance = v1.distance(new Vector(0, 0, 0));
                                    float v2Distance = v2.distance(new Vector(0, 0, 0));
                                    return Float.compare(v1Distance, v2Distance);
                                })
                        .orElseThrow());
    }

    public Set<Vector> getPattern() {
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
    public Shape rotate60(Vector origin) {
        return new Shape(
                this.pattern.stream()
                        .map(v -> v.sub(origin).rotate60().add(origin))
                        .collect(HashSet::new, HashSet::add, HashSet::addAll),
                origin);
    }

    /**
     * Returns a new shape with the same pattern but rotated 60 degrees around the first element of
     * the pattern.
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
    public Shape translate(Vector vector) {
        return new Shape(
                this.pattern.stream()
                        .map(v -> v.add(vector))
                        .collect(HashSet::new, HashSet::add, HashSet::addAll),
                this.origin.add(vector));
    }

    /**
     * Method to match a shape on the board.
     *
     * @param board the board to check the shape on
     * @return the matching translated/rotated shapes
     */
    public List<Shape> match(Board board) {
        HashSet<Shape> matches = new HashSet<>();

        for (Vector tilePosition : board.getTiles().keySet()) {
            // For each tilePosition on the board translate the shape to the tilePosition
            Shape translatedShape = this.translate(tilePosition);
            for (int i = 0; i < Direction.values().length; i++) {
                // Check if the translated shape matches the board
                boolean fullMatch =
                        translatedShape.getPattern().stream()
                                .allMatch(vector -> board.getTiles().containsKey(vector));

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
}

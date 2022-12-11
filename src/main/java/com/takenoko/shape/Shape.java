package com.takenoko.shape;

import com.takenoko.Board;
import com.takenoko.vector.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Shape {
    private final List<Vector> pattern;

    /**
     * Constructor for the Shape class.
     *
     * @param shape the pattern of the shape
     */
    public Shape(List<Vector> shape) {
        this.pattern = shape;
    }

    public List<Vector> getPattern() {
        return new ArrayList<>(this.pattern);
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
                this.pattern.stream().map(v -> v.sub(origin).rotate60().add(origin)).toList());
    }

    /**
     * Returns a new shape with the same pattern but rotated 60 degrees around the first element of
     * the pattern.
     *
     * @return the shape rotated 60 degrees around the first element of the pattern
     */
    public Shape rotate60() {
        return this.rotate60(this.pattern.get(0));
    }

    /**
     * Returns a new shape with the same pattern but translated by the given vector.
     *
     * @param vector the vector to translate the shape by
     * @return a new shape with the same pattern but translated by the given vector
     */
    public Shape translate(Vector vector) {
        return new Shape(this.pattern.stream().map(v -> v.add(vector)).toList());
    }

    /**
     * Method to match a shape on the board.
     *
     * @param board the board to check the shape on
     * @return the matching translated/rotated shapes
     */
    public List<Shape> match(Board board) {
        List<Shape> matches = new ArrayList<>();

        for (Vector tilePosition : board.getTiles().keySet()) {

            // For each tilePosition on the board translate the shape to the tilePosition
            Shape translatedShape = this.translate(tilePosition);

            // Check if the translated shape matches the board
            boolean fullMatch =
                    translatedShape.getPattern().stream()
                            .allMatch(vector -> board.getTiles().containsKey(vector));

            if (fullMatch) {
                matches.add(translatedShape);
            }
        }
        return matches;
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

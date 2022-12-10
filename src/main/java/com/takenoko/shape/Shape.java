package com.takenoko.shape;

import com.takenoko.vector.Vector;
import java.util.ArrayList;
import java.util.List;

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
     * Returns a new shape with the same pattern but rotated 60 degrees around the given origin.
     *
     * @param origin pivot point of the rotation
     * @return the rotated shape
     */
    public Shape rotate60(Vector origin) {
        List<Vector> newPattern = new ArrayList<>();
        for (Vector v : this.pattern) {
            newPattern.add(v.sub(origin).rotate60().add(origin));
        }
        return new Shape(newPattern);
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
        List<Vector> newPattern = new ArrayList<>();

        for (Vector v : this.pattern) {
            newPattern.add(v.add(vector));
        }

        return new Shape(newPattern);
    }

    public List<List<Vector>> match(List<Vector> board) {
        List<List<Vector>> matches = new ArrayList<>();

        for (Vector tile : board) {

            // For each tile on the board translate the shape to the tile
            Shape translatedShape = this.translate(tile);

            // Check if the translated shape matches the board
            boolean fullMatch = true;
            for (Vector vector : translatedShape.getPattern()) {
                fullMatch = true;

                if (!board.contains(vector)) {
                    fullMatch = false;
                }
            }

            if (fullMatch) {
                matches.add(translatedShape.getPattern());
            }
        }

        return matches;
    }
}

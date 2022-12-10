package com.takenoko.shape;

import com.takenoko.vector.Vector;
import java.util.ArrayList;
import java.util.List;

public class Shape {
    List<Vector> pattern;

    /** Constructor for the Shape class. */
    public Shape() {
        this(new ArrayList<>(List.of(new Vector(0, 0, 0))));
    }

    /**
     * Constructor for the Shape class.
     *
     * @param shape the pattern of the shape
     */
    public Shape(List<Vector> shape) {
        this.pattern = shape;

        if (!this.pattern.contains(new Vector(0, 0, 0))) {
            throw new IllegalArgumentException("The shape must contain the origin");
        }
    }

    public List<Vector> getPattern() {
        return new ArrayList<>(this.pattern);
    }

    public Shape rotate60() {
        List<Vector> newPattern = new ArrayList<>();

        for (Vector vector : this.pattern) {
            newPattern.add(vector.rotate60());
        }

        return new Shape(newPattern);
    }

    public Shape translate(Vector vector) {
        List<Vector> newPattern = new ArrayList<>();

        for (Vector tile : this.pattern) {
            newPattern.add(tile.add(vector));
        }

        // Bypass the check in the constructor
        Shape shape = new Shape();
        shape.pattern = newPattern;

        return shape;
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

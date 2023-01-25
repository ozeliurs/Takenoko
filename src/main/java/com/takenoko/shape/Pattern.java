package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/** Class representing a pattern. */
public class Pattern extends Shape {

    /**
     * Constructor for the Pattern class. The origin is the element the closest to the origin of the
     * coordinate system.
     *
     * @param elements the elements of the pattern
     */
    public Pattern(PositionVector... elements) {
        super(elements);
        if (!getElements().containsKey(new PositionVector(0, 0, 0))) {
            throw new IllegalArgumentException("The pattern must contain the origin");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRotatedShapes());
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
            for (Shape rotTransShape : this.translate(tilePosition).getRotatedShapes()) {
                // Check if the translated shape matches the board
                boolean fullMatch =
                        rotTransShape.getElements().keySet().stream()
                                .allMatch(tileMap::containsKey);

                if (fullMatch) {
                    matches.add(rotTransShape);
                }
            }
        }
        return matches.stream().toList();
    }

    /**
     * Returns the ratio of the matching shapes.
     *
     * @param tileMap the tileMap to match the shape on
     * @return the ratio of the matching translated/rotated shapes
     */
    public float matchRatio(Map<PositionVector, Tile> tileMap) {
        long matchedElements =
                IntStream.range(1, getElements().size() + 1)
                        .mapToObj(
                                v ->
                                        new Pattern(
                                                getElements().keySet().stream()
                                                        .limit(v)
                                                        .toArray(PositionVector[]::new)))
                        .filter(p -> !p.match(tileMap).isEmpty())
                        .count();
        return (float) matchedElements / getElements().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pattern pattern = (Pattern) o;
        return getRotatedShapes().equals(pattern.getRotatedShapes());
    }
}

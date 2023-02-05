package com.takenoko.shape;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.Pair;

/** Class representing a pattern. */
public class Pattern extends Shape {
    HashMap<PositionVector, Set<Shape>> cache = new HashMap<>();

    /**
     * Constructor for the Pattern class. The origin is the element the closest to the origin of the
     * coordinate system.
     *
     * @param elements the elements of the pattern
     */
    @SafeVarargs
    public Pattern(Pair<PositionVector, Tile>... elements) {
        super(elements);
        if (!getElements().containsKey(new PositionVector(0, 0, 0))) {
            throw new IllegalArgumentException("The pattern must contain the origin");
        }
    }

    public Pattern(List<Entry<PositionVector, Tile>> toList) {
        super(toList.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
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
     * @param board the board
     * @return the matching translated/rotated shapes
     */
    public List<Shape> match(Board board) {
        Map<PositionVector, Tile> tileMap = board.getTilesWithoutPond();
        // spotless:off
        return tileMap.keySet().stream().flatMap(tilePosition ->
                // For each tilePosition on the board translate the shape to the tilePosition
                cache.computeIfAbsent(tilePosition, p -> this.translate(p).getRotatedShapes()).stream()
                        .filter(rotTransShape -> rotTransShape.getElements().entrySet().stream()
                                .allMatch(e ->
                                        board.isIrrigatedAt(e.getKey()) &&
                                                tileMap.containsKey(e.getKey()) && (
                                                tileMap.get(e.getKey()).getColor().equals(e.getValue().getColor()) || (
                                                        e.getValue().getColor().equals(TileColor.ANY) &&
                                                                !tileMap.get(e.getKey()).getColor().equals(TileColor.NONE)
                                                )
                                        )
                                ))
        ).distinct().toList();
        // spotless:on
    }

    /**
     * Returns the ratio of the matching shapes.
     *
     * @param board@return the ratio of the matching translated/rotated shapes
     */
    public float matchRatio(Board board) {
        // spotless:off
        long matchedElements =
                IntStream.range(1, getElements().size() + 1)
                        .mapToObj(
                                v -> new Pattern(
                                        getElements().entrySet().stream()
                                                .sorted(Comparator.comparingDouble(e -> e.getKey()
                                                        .distance(new PositionVector(0, 0, 0))))
                                                .limit(v)
                                                .toList())
                        )
                        .filter(p -> !p.match(board).isEmpty())
                        .count();
        return (float) matchedElements / getElements().size();
        // spotless:on
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pattern pattern = (Pattern) o;
        return getRotatedShapes().equals(pattern.getRotatedShapes());
    }

    String getColorsString() {
        return this.getElements().values().stream()
                .map(Tile::getColor)
                .distinct()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }
}

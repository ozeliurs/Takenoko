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
    final HashMap<PositionVector, Set<Shape>> cache = new HashMap<>();

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
        return match(board, false);
    }

    public List<Shape> match(Board board, boolean ignoreIrrigation) {
        Map<PositionVector, Tile> tileMap = board.getTilesWithoutPond();
        // spotless:off
        return tileMap.keySet().stream().flatMap(tilePosition ->
                // For each tilePosition on the board translate the shape to the tilePosition
                cache.computeIfAbsent(tilePosition, p -> this.translate(p).getRotatedShapes()).stream()
                        .filter(rotTransShape -> rotTransShape.getElements().entrySet().stream()
                                .allMatch(e ->
                                        (ignoreIrrigation || board.isIrrigatedAt(e.getKey())) &&
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

    public List<Shape> getSubsetMatchPattern(
            Board board, int startingSize, boolean ignoreIrrigation) {
        // spotless:off
        return IntStream.range(startingSize, getElements().size())
                .mapToObj(
                        v -> new Pattern(
                                getElements().entrySet().stream()
                                        .sorted(Comparator.comparingDouble(e -> e.getKey()
                                                .distance(new PositionVector(0, 0, 0))))
                                        .limit(v)
                                        .toList())
                )
                .map(p -> p.match(board, ignoreIrrigation))
                .filter(p -> !p.isEmpty())
                .flatMap(List::stream)
                .toList();
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

    public List<Shape> getShapesToCompletePatternObjective(Board board) {
        List<Shape> matchedShapes = getSubsetMatchPattern(board, 1, true);

        List<Shape> missingShapes = new ArrayList<>();
        for (Shape subset : matchedShapes) {
            // Translate the shape from the origin to a tile of the pattern
            for (PositionVector positionVector : subset.getElements().keySet()) {
                Set<Shape> rotatedShapes = this.translate(positionVector).getRotatedShapes();
                // For each rotation, check if the shape is matching the board
                for (Shape rotatedShape : rotatedShapes) {
                    // Missing conditions
                    Shape missingShape = rotatedShape.getMissingShape(subset);
                    missingShapes.add(missingShape);
                }
            }
        }

        return missingShapes.stream()
                .filter(
                        shape ->
                                shape.getElements().keySet().stream()
                                        .anyMatch(
                                                positionVector ->
                                                        board.getAvailableTilePositions()
                                                                .contains(positionVector)))
                .sorted(Comparator.comparingInt(v -> v.getElements().size()))
                .toList();
    }
}

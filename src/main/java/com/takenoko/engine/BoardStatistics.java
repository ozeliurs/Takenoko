package com.takenoko.engine;

import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;

import java.util.*;

public class BoardStatistics {
    private final EnumMap<TileColor, Integer> tilesPlaced;
    private final EnumMap<ImprovementType, Integer> improvements;

    private float percentageOfIrrigation;
    private float totalNbOfTiles;

    public BoardStatistics(
            Map<TileColor, Integer> tilesPlaced,
            Map<ImprovementType, Integer> improvements,
            float percentageOfIrrigation,
            float totalNbOfTiles) {
        this.tilesPlaced = (EnumMap<TileColor, Integer>) tilesPlaced;
        this.improvements = (EnumMap<ImprovementType, Integer>) improvements;
        this.percentageOfIrrigation = percentageOfIrrigation;
        this.totalNbOfTiles=totalNbOfTiles;
    }

    public BoardStatistics(BoardStatistics boardStatistics) {
        this.tilesPlaced = boardStatistics.tilesPlaced;
        this.percentageOfIrrigation = boardStatistics.percentageOfIrrigation;
        this.improvements = boardStatistics.improvements;
        this.totalNbOfTiles= boardStatistics.totalNbOfTiles;
    }

    public BoardStatistics() {
        this(new EnumMap<>(TileColor.class), new EnumMap<>(ImprovementType.class), 0,0);
    }

    public void updateImprovements(ImprovementType improvementType) {
        if (improvementType == null) {
            throw new IllegalArgumentException();
        }
        if (improvements.containsKey(improvementType)) {
            improvements.replace(improvementType, improvements.get(improvementType) + 1);
        } else {
            improvements.put(improvementType, 1);
        }
    }

    public void updateTilesPlaced(TileColor tileColor) {
        if (tileColor == null) {
            throw new IllegalArgumentException();
        }
        if (tilesPlaced.containsKey(tileColor)) {
            tilesPlaced.put(tileColor, tilesPlaced.get(tileColor) + 1);
        } else {
            tilesPlaced.put(tileColor, 1);
        }
    }

    public void analyzeBoard(Board board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        totalNbOfTiles = board.getTilesWithoutPond().size();
        float irrigatedTiles = 0;
        for (Map.Entry<PositionVector, Tile> entry : board.getTilesWithoutPond().entrySet()) {
            Optional<ImprovementType> improvementType = entry.getValue().getImprovement();
            if (board.isIrrigatedAt(entry.getKey())) irrigatedTiles++;
            improvementType.ifPresent(this::updateImprovements);
            updateTilesPlaced(entry.getValue().getColor());
        }
        percentageOfIrrigation = irrigatedTiles / totalNbOfTiles * 100;
    }

    @Override
    public String toString() {
        StringBuilder statistics = new StringBuilder();
        String lineJump = "\n \t \t \t";
        String indentation = "\t\t* ";
        statistics.append("=========== Board Related Metrics ===========");
        statistics
                .append(lineJump)
                .append("\t -Total number of tiles placed : ")
                .append(totalNbOfTiles)
                .append(lineJump)
                .append("\t -Percentage of irrigated tiles : ")
                .append(percentageOfIrrigation)
                .append("%");
        TreeMap<TileColor, Integer> sortedTilesPlaced = new TreeMap<>(tilesPlaced);
        statistics.append(lineJump).append("\t -Tiles placed :");
        for (TileColor tileColor : sortedTilesPlaced.keySet()) {
            statistics
                    .append(lineJump)
                    .append(indentation)
                    .append(tileColor)
                    .append(" : ")
                    .append(tilesPlaced.get(tileColor));
        }
        return statistics.toString();
    }

    public BoardStatistics copy(){
        return new BoardStatistics(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardStatistics that = (BoardStatistics) o;
        return Float.compare(that.percentageOfIrrigation, percentageOfIrrigation) == 0 && tilesPlaced.equals(that.tilesPlaced) && improvements.equals(that.improvements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tilesPlaced, improvements, percentageOfIrrigation);
    }

    public Map<ImprovementType, Integer> getImprovements() {
        return improvements;
    }

    public float getPercentageOfIrrigation() {
        return percentageOfIrrigation;
    }

    public float getTotalNbOfTiles() {
        return totalNbOfTiles;
    }
}

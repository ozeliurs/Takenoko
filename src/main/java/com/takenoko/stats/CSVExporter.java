package com.takenoko.stats;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.TileColor;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class CSVExporter {
    private static final String CSV_FILE_PATH = "stats/gamestats.csv";
    private static final String FORBIDDEN_CHAR = "🐼";
    File csvPath;

    private List<Pair<BoardStatistics, List<SingleBotStatistics>>> data;

    public CSVExporter() {
        data = new ArrayList<>();

        csvPath = Paths.get(CSV_FILE_PATH).toFile();

        // Create the folder if it doesn't exist
        if (!csvPath.getParentFile().exists() && !csvPath.getParentFile().mkdirs()) {
            throw new RuntimeException("Could not create directory for CSV file");
        }

        // Try and read latest CSV file
        if (csvPath.exists() && csvPath.isFile() && csvPath.canRead()) {
            // Load data
            try (CSVReader reader = new CSVReader(new FileReader(csvPath.toPath().toFile()))) {
                data.addAll(readData(reader.readAll()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addStatistics(
            BoardStatistics boardStatistics, List<SingleBotStatistics> botStatistics) {
        // Load new data
        data.add(Pair.of(boardStatistics, botStatistics));
    }

    public void writeCSV() {
        // Format data into CSV friendly format
        List<String[]> csvData = new ArrayList<>();

        for (Pair<BoardStatistics, List<SingleBotStatistics>> pair : data) {
            csvData.add(formatData(pair).toArray(new String[0]));
        }

        // Write to CSV file

        try (CSVWriter writer = new CSVWriter(new FileWriter(this.csvPath.toPath().toFile()))) {
            writer.writeAll(csvData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== Deserialization =====

    private static List<Pair<BoardStatistics, List<SingleBotStatistics>>> readData(
            List<String[]> data) {
        List<Pair<BoardStatistics, List<SingleBotStatistics>>> result = new ArrayList<>();

        data.forEach(d -> result.add(Pair.of(readBoardStatistics(d), readBotStatistics(d))));

        return result;
    }

    private static BoardStatistics readBoardStatistics(String[] data) {
        Gson gson = new Gson();

        return gson.fromJson(
                data[data.length - 5].replace(FORBIDDEN_CHAR, ","), BoardStatistics.class);
    }

    private static List<SingleBotStatistics> readBotStatistics(String[] data) {
        Gson gson = new Gson();

        List<SingleBotStatistics> botStatistics = new ArrayList<>();

        for (int i = data.length - 3; i < data.length; i++) {
            try {
                botStatistics.add(
                        gson.fromJson(
                                data[i].replace(FORBIDDEN_CHAR, ","), SingleBotStatistics.class));
            } catch (Exception e) {
                botStatistics.add(new SingleBotStatistics());
            }
        }

        return botStatistics;
    }

    // ===== Serialization =====

    private List<String> formatData(Pair<BoardStatistics, List<SingleBotStatistics>> data) {

        Gson gson = new Gson();

        List<String> formattedData = formatBoardStatistics(data.getLeft());

        data.getRight()
                .forEach(
                        singleBotStatistics ->
                                formattedData.addAll(
                                        formatSingleBotStatistics(singleBotStatistics)));

        formattedData.add(gson.toJson(data.getLeft()).replace(",", FORBIDDEN_CHAR));
        for (int i = 0; i < 4; i++) {
            if (i < data.getRight().size()) {
                formattedData.add(gson.toJson(data.getRight().get(i)));
            } else {
                formattedData.add(
                        gson.toJson(new SingleBotStatistics()).replace(",", FORBIDDEN_CHAR));
            }
        }

        // System.out.println(formattedData);

        return formattedData;
    }

    List<String> formatBoardStatistics(BoardStatistics boardStatistics) {
        List<String> formattedData = new ArrayList<>();

        formattedData.add(boardStatistics.tilesPlaced.get(TileColor.GREEN).toString());
        formattedData.add(boardStatistics.tilesPlaced.get(TileColor.PINK).toString());
        formattedData.add(boardStatistics.tilesPlaced.get(TileColor.YELLOW).toString());

        formattedData.add(boardStatistics.improvements.get(ImprovementType.ENCLOSURE).toString());
        formattedData.add(boardStatistics.improvements.get(ImprovementType.FERTILIZER).toString());
        formattedData.add(boardStatistics.improvements.get(ImprovementType.WATERSHED).toString());

        formattedData.add(Float.toString(boardStatistics.percentageOfIrrigation));
        formattedData.add(Float.toString(boardStatistics.totalNbOfTiles));

        return formattedData;
    }

    List<String> formatSingleBotStatistics(SingleBotStatistics singleBotStatistics) {
        List<String> formattedData = new ArrayList<>();

        // <numeric : 4>, totalNbOfAction, <TilesPlaced : 3>, <Weathers : 12>, <Bamboo : 6>,
        // <Actions : 19>

        formattedData.addAll(formatNumericStats(singleBotStatistics.getNumericStats()));
        formattedData.add(Integer.toString(singleBotStatistics.getTotalNbOfAction()));
        formattedData.addAll(formatTilesPlaced(singleBotStatistics.getTilesPlaced()));
        formattedData.addAll(formatWeathers(singleBotStatistics.getWeathers()));
        formattedData.addAll(formatBamboo(singleBotStatistics.getBambooCounter()));
        formattedData.addAll(formatActions(singleBotStatistics.getActions()));

        return formattedData;
    }

    private List<String> formatNumericStats(Map<String, Integer> numericStats) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(numericStats.getOrDefault(SingleBotStatistics.WINS, 0).toString());
        formattedData.add(numericStats.getOrDefault(SingleBotStatistics.LOSSES, 0).toString());
        formattedData.add(
                numericStats.getOrDefault(SingleBotStatistics.IRRIGATIONS_PLACED, 0).toString());
        formattedData.add(numericStats.getOrDefault(SingleBotStatistics.FINAL_SCORE, 0).toString());
        return formattedData;
    }

    private List<String> formatTilesPlaced(Map<TileColor, Integer> tilesPlaced) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(tilesPlaced.getOrDefault(TileColor.GREEN, 0).toString());
        formattedData.add(tilesPlaced.getOrDefault(TileColor.PINK, 0).toString());
        formattedData.add(tilesPlaced.getOrDefault(TileColor.YELLOW, 0).toString());
        return formattedData;
    }

    private List<String> formatWeathers(Map<String, Pair<Integer, Integer>> weathers) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(weathers.getOrDefault("Sunny", Pair.of(0, 0)).getLeft().toString());
        formattedData.add(weathers.getOrDefault("Rainy", Pair.of(0, 0)).getLeft().toString());
        formattedData.add(weathers.getOrDefault("Cloudy", Pair.of(0, 0)).getLeft().toString());
        formattedData.add(weathers.getOrDefault("Windy", Pair.of(0, 0)).getLeft().toString());
        formattedData.add(weathers.getOrDefault("Stormy", Pair.of(0, 0)).getLeft().toString());
        formattedData.add(
                weathers.getOrDefault("QuestionMark", Pair.of(0, 0)).getLeft().toString());
        formattedData.add(weathers.getOrDefault("Sunny", Pair.of(0, 0)).getRight().toString());
        formattedData.add(weathers.getOrDefault("Rainy", Pair.of(0, 0)).getRight().toString());
        formattedData.add(weathers.getOrDefault("Cloudy", Pair.of(0, 0)).getRight().toString());
        formattedData.add(weathers.getOrDefault("Windy", Pair.of(0, 0)).getRight().toString());
        formattedData.add(weathers.getOrDefault("Stormy", Pair.of(0, 0)).getRight().toString());
        formattedData.add(
                weathers.getOrDefault("QuestionMark", Pair.of(0, 0)).getRight().toString());
        return formattedData;
    }

    private Collection<String> formatBamboo(Map<TileColor, Pair<Integer, Integer>> bambooCounter) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(
                bambooCounter.getOrDefault(TileColor.PINK, Pair.of(0, 0)).getLeft().toString());
        formattedData.add(
                bambooCounter.getOrDefault(TileColor.GREEN, Pair.of(0, 0)).getLeft().toString());
        formattedData.add(
                bambooCounter.getOrDefault(TileColor.YELLOW, Pair.of(0, 0)).getLeft().toString());
        formattedData.add(
                bambooCounter.getOrDefault(TileColor.PINK, Pair.of(0, 0)).getRight().toString());
        formattedData.add(
                bambooCounter.getOrDefault(TileColor.GREEN, Pair.of(0, 0)).getRight().toString());
        formattedData.add(
                bambooCounter.getOrDefault(TileColor.YELLOW, Pair.of(0, 0)).getRight().toString());
        return formattedData;
    }

    private Collection<String> formatActions(Map<String, Integer> actions) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(actions.getOrDefault("ForcedMovePandaAction", 0).toString());
        formattedData.add(actions.getOrDefault("MoveGardenerAction", 0).toString());
        formattedData.add(actions.getOrDefault("MovePandaAction", 0).toString());
        formattedData.add(actions.getOrDefault("GrowBambooAction", 0).toString());
        formattedData.add(actions.getOrDefault("ApplyImprovementAction", 0).toString());
        formattedData.add(
                actions.getOrDefault("ApplyImprovementFromInventoryAction", 0).toString());
        formattedData.add(actions.getOrDefault("DrawImprovementAction", 0).toString());
        formattedData.add(actions.getOrDefault("StoreImprovementAction", 0).toString());
        formattedData.add(actions.getOrDefault("DrawIrrigationAction", 0).toString());
        formattedData.add(actions.getOrDefault("PlaceIrrigationAction", 0).toString());
        formattedData.add(actions.getOrDefault("PlaceIrrigationFromInventoryAction", 0).toString());
        formattedData.add(actions.getOrDefault("StoreIrrigationInInventoryAction", 0).toString());
        formattedData.add(actions.getOrDefault("DrawObjectiveAction", 0).toString());
        formattedData.add(actions.getOrDefault("RedeemObjectiveAction", 0).toString());
        formattedData.add(actions.getOrDefault("DrawTileAction", 0).toString());
        formattedData.add(actions.getOrDefault("PlaceTileAction", 0).toString());
        formattedData.add(actions.getOrDefault("PlaceTileWithImprovementAction", 0).toString());
        formattedData.add(actions.getOrDefault("ChooseAndApplyWeatherAction", 0).toString());
        formattedData.add(actions.getOrDefault("ChooseIfApplyWeatherAction", 0).toString());
        return formattedData;
    }
}

package com.takenoko.stats;

import com.opencsv.CSVReader;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.TileColor;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class CSVExporter {
    private static final String CSV_FILE_PATH = "stats/gamestats.csv";
    private static final Integer BOARD_STATISTICS_SIZE = 8;
    private static final Integer BOT_STATISTICS_SIZE = 45;

    private List<Pair<BoardStatistics, List<SingleBotStatistics>>> data;

    public CSVExporter() {
        data = new ArrayList<>();

        File csvPath = Paths.get(CSV_FILE_PATH).toFile();

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

    public void loadStatistics(
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
    }

    // ===== Deserialization =====

    private static List<Pair<BoardStatistics, List<SingleBotStatistics>>> readData(
            List<String[]> data) {
        List<Pair<BoardStatistics, List<SingleBotStatistics>>> result = new ArrayList<>();

        // For each Line
        for (String[] row : data) {
            // Parse the BoardStatistics
            BoardStatistics boardStatistics =
                    parseBoardStatistics(Arrays.stream(row).limit(BOARD_STATISTICS_SIZE).toList());

            Pair<BoardStatistics, List<SingleBotStatistics>> pair =
                    Pair.of(boardStatistics, new ArrayList<>());

            for (int i = 0; i < 4; i++) {
                // Get the next 4 elements
                pair.getRight()
                        .add(
                                parseBotStatistics(
                                        Arrays.stream(row)
                                                .skip(
                                                        (long) BOARD_STATISTICS_SIZE
                                                                + (long) i * BOT_STATISTICS_SIZE)
                                                .limit(BOT_STATISTICS_SIZE)
                                                .toList()));
            }
            // Add the boardStatistics to the result
            result.add(pair);
        }
        return result;
    }

    private static BoardStatistics parseBoardStatistics(List<String> data) {
        // TilePlaced.GREEN, TilePlaced.PINK, TilePlaced.YELLOW, ImprovementType.ENCLOSURE,
        // ImprovementType.FERTILIZER, ImprovementType.WATERSHED, percentageOfIrrigation,
        // totalNbOfTiles

        if (data.size() != BOARD_STATISTICS_SIZE) {
            throw new RuntimeException("Invalid data size");
        }

        Map<TileColor, Integer> tilesPlaced = new EnumMap<>(TileColor.class);
        Map<ImprovementType, Integer> improvements = new EnumMap<>(ImprovementType.class);

        tilesPlaced.put(TileColor.GREEN, Integer.parseInt(data.get(0)));
        tilesPlaced.put(TileColor.PINK, Integer.parseInt(data.get(1)));
        tilesPlaced.put(TileColor.YELLOW, Integer.parseInt(data.get(2)));

        improvements.put(ImprovementType.ENCLOSURE, Integer.parseInt(data.get(3)));
        improvements.put(ImprovementType.FERTILIZER, Integer.parseInt(data.get(4)));
        improvements.put(ImprovementType.WATERSHED, Integer.parseInt(data.get(5)));

        // Map<TileColor, Integer> tilesPlaced, Map<ImprovementType, Integer> improvements, float
        // percentageOfIrrigation, float totalNbOfTiles
        return new BoardStatistics(
                tilesPlaced,
                improvements,
                Float.parseFloat(data.get(6)),
                Float.parseFloat(data.get(7)));
    }

    private static SingleBotStatistics parseBotStatistics(List<String> data) {
        // Wins, Losses, Irrigations Placed, Final Score, totalNbOfAction, <TilesPlaced : 3>,
        // <Weathers : 12>, <Bamboo : 6>, <Actions : 19>, <Objectives : TODO>

        return new SingleBotStatistics(
                parseNumericStats(data.stream().limit(4).toList()), // 0 to 3
                Map.of(), // TODO
                parseBamboo(data.stream().skip(19).limit(6).toList()), // 20 to 25
                parseTilesPlaced(data.stream().skip(4).limit(3).toList()), // 5 to 7
                parseWeathers(data.stream().skip(7).limit(12).toList()), // 8 to 19
                parseActions(data.stream().skip(25).limit(19).toList()), // 26 to 44
                Integer.parseInt(data.get(4)) // 4
                );
    }

    private static Map<String, Integer> parseActions(List<String> data) {
        HashMap<String, Integer> actionsIndex = new HashMap<>();
        actionsIndex.put("ForcedMovePandaAction", 0);
        actionsIndex.put("MoveGardenerAction", 1);
        actionsIndex.put("MovePandaAction", 2);
        actionsIndex.put("GrowBambooAction", 3);
        actionsIndex.put("ApplyImprovementAction", 4);
        actionsIndex.put("ApplyImprovementFromInventoryAction", 5);
        actionsIndex.put("DrawImprovementAction", 6);
        actionsIndex.put("StoreImprovementAction", 7);
        actionsIndex.put("DrawIrrigationAction", 8);
        actionsIndex.put("PlaceIrrigationAction", 9);
        actionsIndex.put("PlaceIrrigationFromInventoryAction", 10);
        actionsIndex.put("StoreIrrigationInInventoryAction", 11);
        actionsIndex.put("DrawObjectiveAction", 12);
        actionsIndex.put("RedeemObjectiveAction", 13);
        actionsIndex.put("DrawTileAction", 14);
        actionsIndex.put("PlaceTileAction", 15);
        actionsIndex.put("PlaceTileWithImprovementAction", 16);
        actionsIndex.put("ChooseAndApplyWeatherAction", 17);
        actionsIndex.put("ChooseIfApplyWeatherAction", 18);

        Map<String, Integer> actions = new HashMap<>();

        actionsIndex
                .keySet()
                .forEach(
                        action ->
                                actions.put(
                                        action,
                                        Integer.parseInt(data.get(actionsIndex.get(action)))));

        return actions;
    }

    private static Map<String, Pair<Integer, Integer>> parseWeathers(List<String> data) {
        Map<String, Pair<Integer, Integer>> weathers = new HashMap<>();
        weathers.put(
                "Sunny", Pair.of(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(6))));
        weathers.put(
                "Rainy", Pair.of(Integer.parseInt(data.get(1)), Integer.parseInt(data.get(7))));
        weathers.put(
                "Cloudy", Pair.of(Integer.parseInt(data.get(2)), Integer.parseInt(data.get(8))));
        weathers.put(
                "Windy", Pair.of(Integer.parseInt(data.get(3)), Integer.parseInt(data.get(9))));
        weathers.put(
                "Stormy", Pair.of(Integer.parseInt(data.get(4)), Integer.parseInt(data.get(10))));
        weathers.put(
                "QuestionMark",
                Pair.of(Integer.parseInt(data.get(5)), Integer.parseInt(data.get(11))));
        return weathers;
    }

    private static Map<TileColor, Pair<Integer, Integer>> parseBamboo(List<String> data) {
        Map<TileColor, Pair<Integer, Integer>> bamboo = new EnumMap<>(TileColor.class);
        bamboo.put(
                TileColor.GREEN,
                Pair.of(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(3))));
        bamboo.put(
                TileColor.PINK,
                Pair.of(Integer.parseInt(data.get(1)), Integer.parseInt(data.get(4))));
        bamboo.put(
                TileColor.YELLOW,
                Pair.of(Integer.parseInt(data.get(2)), Integer.parseInt(data.get(5))));
        return bamboo;
    }

    private static Map<TileColor, Integer> parseTilesPlaced(List<String> data) {
        Map<TileColor, Integer> tilesPlaced = new EnumMap<>(TileColor.class);
        tilesPlaced.put(TileColor.GREEN, Integer.parseInt(data.get(0)));
        tilesPlaced.put(TileColor.PINK, Integer.parseInt(data.get(1)));
        tilesPlaced.put(TileColor.YELLOW, Integer.parseInt(data.get(2)));
        return tilesPlaced;
    }

    private static Map<String, Integer> parseNumericStats(List<String> data) {
        Map<String, Integer> numericStats = new HashMap<>();
        numericStats.put(SingleBotStatistics.WINS, Integer.parseInt(data.get(0)));
        numericStats.put(SingleBotStatistics.LOSSES, Integer.parseInt(data.get(1)));
        numericStats.put(SingleBotStatistics.IRRIGATIONS_PLACED, Integer.parseInt(data.get(2)));
        numericStats.put(SingleBotStatistics.FINAL_SCORE, Integer.parseInt(data.get(3)));
        return numericStats;
    }

    // ===== Serialization =====

    private List<String> formatData(Pair<BoardStatistics, List<SingleBotStatistics>> data) {

        List<String> formattedData = formatBoardStatistics(data.getLeft());

        data.getRight()
                .forEach(
                        singleBotStatistics ->
                                formattedData.addAll(
                                        formatSingleBotStatistics(singleBotStatistics)));

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
        formattedData.add(numericStats.get(SingleBotStatistics.WINS).toString());
        formattedData.add(numericStats.get(SingleBotStatistics.LOSSES).toString());
        formattedData.add(numericStats.get(SingleBotStatistics.IRRIGATIONS_PLACED).toString());
        formattedData.add(numericStats.get(SingleBotStatistics.FINAL_SCORE).toString());
        return formattedData;
    }

    private List<String> formatTilesPlaced(Map<TileColor, Integer> tilesPlaced) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(tilesPlaced.get(TileColor.GREEN).toString());
        formattedData.add(tilesPlaced.get(TileColor.PINK).toString());
        formattedData.add(tilesPlaced.get(TileColor.YELLOW).toString());
        return formattedData;
    }

    private List<String> formatWeathers(Map<String, Pair<Integer, Integer>> weathers) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(weathers.get("Sunny").getLeft().toString());
        formattedData.add(weathers.get("Rainy").getLeft().toString());
        formattedData.add(weathers.get("Cloudy").getLeft().toString());
        formattedData.add(weathers.get("Windy").getLeft().toString());
        formattedData.add(weathers.get("Stormy").getLeft().toString());
        formattedData.add(weathers.get("QuestionMark").getLeft().toString());
        formattedData.add(weathers.get("Sunny").getRight().toString());
        formattedData.add(weathers.get("Rainy").getRight().toString());
        formattedData.add(weathers.get("Cloudy").getRight().toString());
        formattedData.add(weathers.get("Windy").getRight().toString());
        formattedData.add(weathers.get("Stormy").getRight().toString());
        formattedData.add(weathers.get("QuestionMark").getRight().toString());
        return formattedData;
    }

    private Collection<String> formatBamboo(Map<TileColor, Pair<Integer, Integer>> bambooCounter) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(bambooCounter.get(TileColor.PINK).getLeft().toString());
        formattedData.add(bambooCounter.get(TileColor.GREEN).getLeft().toString());
        formattedData.add(bambooCounter.get(TileColor.YELLOW).getLeft().toString());
        formattedData.add(bambooCounter.get(TileColor.PINK).getRight().toString());
        formattedData.add(bambooCounter.get(TileColor.GREEN).getRight().toString());
        formattedData.add(bambooCounter.get(TileColor.YELLOW).getRight().toString());
        return formattedData;
    }

    private Collection<String> formatActions(Map<String, Integer> actions) {
        List<String> formattedData = new ArrayList<>();
        formattedData.add(actions.get("ForcedMovePandaAction").toString());
        formattedData.add(actions.get("MoveGardenerAction").toString());
        formattedData.add(actions.get("MovePandaAction").toString());
        formattedData.add(actions.get("GrowBambooAction").toString());
        formattedData.add(actions.get("ApplyImprovementAction").toString());
        formattedData.add(actions.get("ApplyImprovementFromInventoryAction").toString());
        formattedData.add(actions.get("DrawImprovementAction").toString());
        formattedData.add(actions.get("StoreImprovementAction").toString());
        formattedData.add(actions.get("DrawIrrigationAction").toString());
        formattedData.add(actions.get("PlaceIrrigationAction").toString());
        formattedData.add(actions.get("PlaceIrrigationFromInventoryAction").toString());
        formattedData.add(actions.get("StoreIrrigationInInventoryAction").toString());
        formattedData.add(actions.get("DrawObjectiveAction").toString());
        formattedData.add(actions.get("RedeemObjectiveAction").toString());
        formattedData.add(actions.get("DrawTileAction").toString());
        formattedData.add(actions.get("PlaceTileAction").toString());
        formattedData.add(actions.get("PlaceTileWithImprovementAction").toString());
        formattedData.add(actions.get("ChooseAndApplyWeatherAction").toString());
        formattedData.add(actions.get("ChooseIfApplyWeatherAction").toString());
        return formattedData;
    }
}

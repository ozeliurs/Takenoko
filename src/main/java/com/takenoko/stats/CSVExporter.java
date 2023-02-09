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

    private final List<Pair<BoardStatistics, List<SingleBotStatistics>>> data;

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
        List<String[]> contents = getContents(data);

        // Write to CSV file
        try (CSVWriter writer = new CSVWriter(new FileWriter(this.csvPath.toPath().toFile()))) {
            writer.writeAll(contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== Deserialization =====

    /**
     * Deserialize the data back into classes
     *
     * @param data The data to be deserialized
     * @return The serialized data
     */
    private static List<Pair<BoardStatistics, List<SingleBotStatistics>>> readData(
            List<String[]> data) {
        List<Pair<BoardStatistics, List<SingleBotStatistics>>> result = new ArrayList<>();

        toMap(data.stream().map(v -> Arrays.stream(v).toList()).toList())
                .forEach(d -> result.add(Pair.of(readBoardStatistics(d), readBotStatistics(d))));

        return result;
    }

    /**
     * Use Gson to Deserialize the BoardStatistics from the "CSV" file
     *
     * @param data The full csv line
     * @return The BoardStatistics
     */
    private static BoardStatistics readBoardStatistics(HashMap<String, String> data) {
        Gson gson = new Gson();

        return gson.fromJson(
                data.get("serializedBoard").replace(FORBIDDEN_CHAR, ","), BoardStatistics.class);
    }

    /**
     * Use Gson to Deserialize the SingleBotStatistics from the "CSV" file
     *
     * @param data The full csv line
     * @return A list of SingleBotStatistics
     */
    private static List<SingleBotStatistics> readBotStatistics(HashMap<String, String> data) {
        Gson gson = new Gson();

        List<SingleBotStatistics> botStatistics = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            try {
                botStatistics.add(
                        gson.fromJson(
                                data.get("Bot<" + i + ">ChooseIfApplyWeatherAction")
                                        .replace(FORBIDDEN_CHAR, ","),
                                SingleBotStatistics.class));
            } catch (Exception ignored) {
                // Ignore
            }
        }

        return botStatistics;
    }

    // ===== Serialization =====

    /**
     * Serialize the data into a CSV friendly format
     *
     * @param data The data to serialize
     * @return The serialized data
     */
    private static List<String[]> getContents(
            List<Pair<BoardStatistics, List<SingleBotStatistics>>> data) {
        List<HashMap<String, String>> contents = new ArrayList<>();
        for (Pair<BoardStatistics, List<SingleBotStatistics>> entry : data) {
            HashMap<String, String> content = formatBoardStatistics(entry.getLeft());
            int i = 0;
            for (SingleBotStatistics botStats : entry.getRight()) {
                content.putAll(formatSingleBotStatistics(botStats, i));
                i++;
            }
            contents.add(content);
        }
        return toList(contents).stream().map(e -> e.toArray(new String[0])).toList();
    }

    /**
     * Serialize the BoardStatistics into a CSV friendly format
     *
     * @param boardStatistics The BoardStatistics to serialize
     * @return The serialized BoardStatistics
     */
    private static HashMap<String, String> formatBoardStatistics(BoardStatistics boardStatistics) {
        HashMap<String, String> entries = new HashMap<>();
        Gson gson = new Gson();

        entries.put(
                "TotalGreenTilesPlaced",
                boardStatistics.tilesPlaced.get(TileColor.GREEN).toString());
        entries.put(
                "TotalPinkTilesPlaced", boardStatistics.tilesPlaced.get(TileColor.PINK).toString());
        entries.put(
                "TotalYellowTilesPlaced",
                boardStatistics.tilesPlaced.get(TileColor.YELLOW).toString());
        entries.put(
                "TotalEnclosureImprovementsPlaced",
                boardStatistics.improvements.get(ImprovementType.ENCLOSURE).toString());
        entries.put(
                "TotalFertilizerImprovementsPlaced",
                boardStatistics.improvements.get(ImprovementType.FERTILIZER).toString());
        entries.put(
                "TotalWatershedImprovementsPlaced",
                boardStatistics.improvements.get(ImprovementType.WATERSHED).toString());
        entries.put(
                "percentageOfIrrigation", Float.toString(boardStatistics.percentageOfIrrigation));
        entries.put("totalNbOfTiles", Float.toString(boardStatistics.totalNbOfTiles));
        entries.put("serializedBoard", gson.toJson(boardStatistics).replace(",", FORBIDDEN_CHAR));

        return entries;
    }

    /**
     * Serialize the SingleBotStatistics into a CSV friendly format
     *
     * @param singleBotStatistics The SingleBotStatistics to serialize
     * @param offset The offset of the bot
     * @return The serialized SingleBotStatistics
     */
    private static HashMap<String, String> formatSingleBotStatistics(
            SingleBotStatistics singleBotStatistics, int offset) {
        if (singleBotStatistics == null) {
            return new HashMap<>();
        }

        HashMap<String, String> entries = new HashMap<>();
        Gson gson = new Gson();

        entries.put(
                "Bot<" + offset + ">Wins",
                singleBotStatistics
                        .getNumericStats()
                        .getOrDefault(SingleBotStatistics.WINS, 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">Losses",
                singleBotStatistics
                        .getNumericStats()
                        .getOrDefault(SingleBotStatistics.LOSSES, 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">IrrigationsPlaced",
                singleBotStatistics
                        .getNumericStats()
                        .getOrDefault(SingleBotStatistics.IRRIGATIONS_PLACED, 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">FinalScore",
                singleBotStatistics
                        .getNumericStats()
                        .getOrDefault(SingleBotStatistics.FINAL_SCORE, 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">TotalNbOfAction",
                Integer.toString(singleBotStatistics.getTotalNbOfAction()));

        entries.put(
                "Bot<" + offset + ">GreenTilesPlaced",
                singleBotStatistics.getTilesPlaced().getOrDefault(TileColor.GREEN, 0).toString());
        entries.put(
                "Bot<" + offset + ">PinkTilesPlaced",
                singleBotStatistics.getTilesPlaced().getOrDefault(TileColor.PINK, 0).toString());
        entries.put(
                "Bot<" + offset + ">YellowTilesPlaced",
                singleBotStatistics.getTilesPlaced().getOrDefault(TileColor.YELLOW, 0).toString());

        entries.put(
                "Bot<" + offset + ">AppliedSunny",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Sunny", Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">AppliedRainy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Rainy", Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">AppliedCloudy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Cloudy", Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">AppliedWindy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Windy", Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">AppliedStormy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Stormy", Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">AppliedQuestionMark",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("QuestionMark", Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">RolledSunny",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Sunny", Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">RolledRainy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Rainy", Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">RolledCloudy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Cloudy", Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">RolledWindy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Windy", Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">RolledStormy",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("Stormy", Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">RolledQuestionMark",
                singleBotStatistics
                        .getWeathers()
                        .getOrDefault("QuestionMark", Pair.of(0, 0))
                        .getRight()
                        .toString());

        entries.put(
                "Bot<" + offset + ">GreenBambooPlaced",
                singleBotStatistics
                        .getBambooCounter()
                        .getOrDefault(TileColor.GREEN, Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">PinkBambooPlaced",
                singleBotStatistics
                        .getBambooCounter()
                        .getOrDefault(TileColor.PINK, Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">YellowBambooPlaced",
                singleBotStatistics
                        .getBambooCounter()
                        .getOrDefault(TileColor.YELLOW, Pair.of(0, 0))
                        .getLeft()
                        .toString());
        entries.put(
                "Bot<" + offset + ">GreenBambooEaten",
                singleBotStatistics
                        .getBambooCounter()
                        .getOrDefault(TileColor.GREEN, Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">PinkBambooEaten",
                singleBotStatistics
                        .getBambooCounter()
                        .getOrDefault(TileColor.PINK, Pair.of(0, 0))
                        .getRight()
                        .toString());
        entries.put(
                "Bot<" + offset + ">YellowBambooEaten",
                singleBotStatistics
                        .getBambooCounter()
                        .getOrDefault(TileColor.YELLOW, Pair.of(0, 0))
                        .getRight()
                        .toString());

        entries.put(
                "Bot<" + offset + ">ForcedMovePandaAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("ForcedMovePandaAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">MoveGardenerAction",
                singleBotStatistics.getActions().getOrDefault("MoveGardenerAction", 0).toString());
        entries.put(
                "Bot<" + offset + ">MovePandaAction",
                singleBotStatistics.getActions().getOrDefault("MovePandaAction", 0).toString());
        entries.put(
                "Bot<" + offset + ">GrowBambooAction",
                singleBotStatistics.getActions().getOrDefault("GrowBambooAction", 0).toString());
        entries.put(
                "Bot<" + offset + ">ApplyImprovementAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("ApplyImprovementAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">ApplyImprovementFromInventoryAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("ApplyImprovementFromInventoryAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">DrawImprovementAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("DrawImprovementAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">StoreImprovementAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("StoreImprovementAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">DrawIrrigationAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("DrawIrrigationAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">PlaceIrrigationAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("PlaceIrrigationAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">PlaceIrrigationFromInventoryAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("PlaceIrrigationFromInventoryAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">StoreIrrigationInInventoryAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("StoreIrrigationInInventoryAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">DrawObjectiveAction",
                singleBotStatistics.getActions().getOrDefault("DrawObjectiveAction", 0).toString());
        entries.put(
                "Bot<" + offset + ">RedeemObjectiveAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("RedeemObjectiveAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">DrawTileAction",
                singleBotStatistics.getActions().getOrDefault("DrawTileAction", 0).toString());
        entries.put(
                "Bot<" + offset + ">PlaceTileAction",
                singleBotStatistics.getActions().getOrDefault("PlaceTileAction", 0).toString());
        entries.put(
                "Bot<" + offset + ">PlaceTileWithImprovementAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("PlaceTileWithImprovementAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">ChooseAndApplyWeatherAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("ChooseAndApplyWeatherAction", 0)
                        .toString());
        entries.put(
                "Bot<" + offset + ">ChooseIfApplyWeatherAction",
                singleBotStatistics
                        .getActions()
                        .getOrDefault("ChooseIfApplyWeatherAction", 0)
                        .toString());
        try {
            entries.put(
                    "Bot<" + offset + ">Serialized",
                    gson.toJson(singleBotStatistics).replace(",", FORBIDDEN_CHAR));
        } catch (Exception e) {
            entries.put(
                    "Bot<" + offset + ">Serialized",
                    gson.toJson(new SingleBotStatistics()).replace(",", FORBIDDEN_CHAR));
        }

        return entries;
    }

    /**
     * Converts a list of HashMaps to a list of lists.
     * @param entries The list of HashMaps.
     * @return The list of lists.
     */
    private static List<List<String>> toList(List<HashMap<String, String>> entries) {
        // Build the header
        List<String> header = new ArrayList<>();
        for (HashMap<String, String> entry : entries) {
            for (String key : entry.keySet()) {
                if (!header.contains(key)) {
                    header.add(key);
                }
            }
        }

        // Build the body
        List<List<String>> body = new ArrayList<>();
        body.add(header);
        for (HashMap<String, String> entry : entries) {
            List<String> row = new ArrayList<>();
            for (String key : header) {
                row.add(entry.getOrDefault(key, ""));
            }
            body.add(row);
        }

        return body;
    }

    /**
     * Converts a list of lists to a list of HashMaps.
     * @param data The list of lists.
     * @return The list of HashMaps.
     */
    private static List<HashMap<String, String>> toMap(List<List<String>> data) {
        List<HashMap<String, String>> entries = new ArrayList<>();

        // Get the header
        List<String> header = data.get(0);

        // Build the entries
        for (int i = 1; i < data.size(); i++) {
            HashMap<String, String> entry = new HashMap<>();
            List<String> row = data.get(i);
            for (int j = 0; j < header.size(); j++) {
                entry.put(header.get(j), row.get(j));
            }
            entries.add(entry);
        }

        return entries;
    }
}

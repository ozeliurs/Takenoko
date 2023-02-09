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
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CSVExporter {
    private static final String CSV_FILE_PATH = "stats/gamestats.csv";
    private static final String FORBIDDEN_CHAR = "🐼";
    private static final int NUMBER_OF_BOTS = 2;
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

        for (int i = 0; i < NUMBER_OF_BOTS; i++) {
            try {
                System.out.println(data.get("Bot<" + i + ">Serialized"));
                botStatistics.add(
                        gson.fromJson(
                                data.get("Bot<" + i + ">Serialized").replace(FORBIDDEN_CHAR, ","),
                                SingleBotStatistics.class));
            } catch (Exception e) {
                System.out.println("Error while reading bot statistics: " + e.getMessage());
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
                Objects.toString(
                        singleBotStatistics
                                .getNumericStats()
                                .getOrDefault(SingleBotStatistics.WINS, 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">Losses",
                Objects.toString(
                        singleBotStatistics
                                .getNumericStats()
                                .getOrDefault(SingleBotStatistics.LOSSES, 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">IrrigationsPlaced",
                Objects.toString(
                        singleBotStatistics
                                .getNumericStats()
                                .getOrDefault(SingleBotStatistics.IRRIGATIONS_PLACED, 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">FinalScore",
                Objects.toString(
                        singleBotStatistics
                                .getNumericStats()
                                .getOrDefault(SingleBotStatistics.FINAL_SCORE, 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">TotalNbOfAction",
                Integer.toString(singleBotStatistics.getTotalNbOfAction()));

        entries.put(
                "Bot<" + offset + ">GreenTilesPlaced",
                Objects.toString(
                        singleBotStatistics.getTilesPlaced().getOrDefault(TileColor.GREEN, 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">PinkTilesPlaced",
                Objects.toString(
                        singleBotStatistics.getTilesPlaced().getOrDefault(TileColor.PINK, 0), "0"));
        entries.put(
                "Bot<" + offset + ">YellowTilesPlaced",
                Objects.toString(
                        singleBotStatistics.getTilesPlaced().getOrDefault(TileColor.YELLOW, 0),
                        "0"));

        entries.put(
                "Bot<" + offset + ">AppliedSunny",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Sunny", MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">AppliedRainy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Rainy", MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">AppliedCloudy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Cloudy", MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">AppliedWindy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Windy", MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">AppliedStormy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Stormy", MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">AppliedQuestionMark",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("QuestionMark", MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">RolledSunny",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Sunny", MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">RolledRainy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Rainy", MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">RolledCloudy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Cloudy", MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">RolledWindy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Windy", MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">RolledStormy",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("Stormy", MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">RolledQuestionMark",
                Objects.toString(
                        singleBotStatistics
                                .getWeathers()
                                .getOrDefault("QuestionMark", MutablePair.of(0, 0))
                                .getRight(),
                        "0"));

        entries.put(
                "Bot<" + offset + ">GreenBambooPlaced",
                Objects.toString(
                        singleBotStatistics
                                .getBambooCounter()
                                .getOrDefault(TileColor.GREEN, MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">PinkBambooPlaced",
                Objects.toString(
                        singleBotStatistics
                                .getBambooCounter()
                                .getOrDefault(TileColor.PINK, MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">YellowBambooPlaced",
                Objects.toString(
                        singleBotStatistics
                                .getBambooCounter()
                                .getOrDefault(TileColor.YELLOW, MutablePair.of(0, 0))
                                .getLeft(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">GreenBambooEaten",
                Objects.toString(
                        singleBotStatistics
                                .getBambooCounter()
                                .getOrDefault(TileColor.GREEN, MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">PinkBambooEaten",
                Objects.toString(
                        singleBotStatistics
                                .getBambooCounter()
                                .getOrDefault(TileColor.PINK, MutablePair.of(0, 0))
                                .getRight(),
                        "0"));
        entries.put(
                "Bot<" + offset + ">YellowBambooEaten",
                Objects.toString(
                        singleBotStatistics
                                .getBambooCounter()
                                .getOrDefault(TileColor.YELLOW, MutablePair.of(0, 0))
                                .getRight(),
                        "0"));

        entries.put(
                "Bot<" + offset + ">ForcedMovePandaAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("ForcedMovePandaAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">MoveGardenerAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("MoveGardenerAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">MovePandaAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("MovePandaAction", 0), "0"));
        entries.put(
                "Bot<" + offset + ">GrowBambooAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("GrowBambooAction", 0), "0"));
        entries.put(
                "Bot<" + offset + ">ApplyImprovementAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("ApplyImprovementAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">ApplyImprovementFromInventoryAction",
                Objects.toString(
                        singleBotStatistics
                                .getActions()
                                .getOrDefault("ApplyImprovementFromInventoryAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">DrawImprovementAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("DrawImprovementAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">StoreImprovementAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("StoreImprovementAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">DrawIrrigationAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("DrawIrrigationAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">PlaceIrrigationAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("PlaceIrrigationAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">PlaceIrrigationFromInventoryAction",
                Objects.toString(
                        singleBotStatistics
                                .getActions()
                                .getOrDefault("PlaceIrrigationFromInventoryAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">StoreIrrigationInInventoryAction",
                Objects.toString(
                        singleBotStatistics
                                .getActions()
                                .getOrDefault("StoreIrrigationInInventoryAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">DrawObjectiveAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("DrawObjectiveAction", 0)));
        entries.put(
                "Bot<" + offset + ">RedeemObjectiveAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("RedeemObjectiveAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">DrawTileAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("DrawTileAction", 0), "0"));
        entries.put(
                "Bot<" + offset + ">PlaceTileAction",
                Objects.toString(
                        singleBotStatistics.getActions().getOrDefault("PlaceTileAction", 0), "0"));
        entries.put(
                "Bot<" + offset + ">PlaceTileWithImprovementAction",
                Objects.toString(
                        singleBotStatistics
                                .getActions()
                                .getOrDefault("PlaceTileWithImprovementAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">ChooseAndApplyWeatherAction",
                Objects.toString(
                        singleBotStatistics
                                .getActions()
                                .getOrDefault("ChooseAndApplyWeatherAction", 0),
                        "0"));
        entries.put(
                "Bot<" + offset + ">ChooseIfApplyWeatherAction",
                Objects.toString(
                        singleBotStatistics
                                .getActions()
                                .getOrDefault("ChooseIfApplyWeatherAction", 0),
                        "0"));
        try {
            entries.put(
                    "Bot<" + offset + ">Serialized",
                    gson.toJson(singleBotStatistics).replace(",", FORBIDDEN_CHAR));
        } catch (Exception e) {
            System.out.println(
                    "Error while serializing Bot<"
                            + offset
                            + ">Serialized statistics"
                            + e.getMessage());
            entries.put(
                    "Bot<" + offset + ">Serialized",
                    gson.toJson(new SingleBotStatistics()).replace(",", FORBIDDEN_CHAR));
        }

        return entries;
    }

    /**
     * Converts a list of HashMaps to a list of lists.
     *
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
     *
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

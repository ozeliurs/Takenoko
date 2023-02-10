package com.takenoko.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BotCSVExporter extends CSVExporter {
    private HashMap<String, SingleBotStatistics> botsData;

    private final String[] header = new String[] {"Bot Name", "Wins", "Losses", "Final Score"};

    public BotCSVExporter(String filePath) {
        super(filePath);
    }

    @Override
    protected void readData(List<String[]> data) {
        this.botsData = new HashMap<>();
        for (String[] line :
                data.stream()
                        .filter(line -> !line[0].equals("Bot Name"))
                        .toArray(String[][]::new)) {
            botsData.put(
                    line[0],
                    new SingleBotStatistics(
                            Integer.parseInt(line[1]),
                            Integer.parseInt(line[2]),
                            Integer.parseInt(line[3])));
        }
    }

    @Override
    protected List<String[]> writeData() {
        List<String[]> contents = new ArrayList<>();
        contents.add(header);
        botsData.forEach(
                (key, value) ->
                        contents.add(
                                new String[] {
                                    key,
                                    String.valueOf(value.getWins()),
                                    String.valueOf(value.getLosses()),
                                    String.valueOf(value.getFinalScore())
                                }));
        return contents;
    }

    public void addStatistics(BotStatistics botStatistics) {
        botStatistics.forEach(
                (key, value) -> {
                    if (botsData.containsKey(key.getName())) {
                        botsData.get(key.getName()).addStatistics(value);
                    } else {
                        botsData.put(key.getName(), value);
                    }
                });
    }
}

package com.takenoko.stats;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;

public abstract class CSVExporter {
    File csvPath;

    @SuppressWarnings("java:S112")
    protected CSVExporter(String filePath) {
        csvPath = Paths.get(filePath).toFile();

        // Create the folder if it doesn't exist
        if (!csvPath.getParentFile().exists() && !csvPath.getParentFile().mkdirs()) {
            throw new RuntimeException("Could not create directory for CSV file");
        }

        // Try and read latest CSV file
        if (csvPath.exists() && csvPath.isFile() && csvPath.canRead()) {
            // Load data
            try (CSVReader reader = new CSVReader(new FileReader(csvPath.toPath().toFile()))) {
                readData(reader.readAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeCSV() {
        // Format data into CSV friendly format
        List<String[]> contents = writeData();

        // Write to CSV file
        try (CSVWriter writer = new CSVWriter(new FileWriter(this.csvPath.toPath().toFile()))) {
            writer.writeAll(contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void readData(List<String[]> data);

    protected abstract List<String[]> writeData();
}

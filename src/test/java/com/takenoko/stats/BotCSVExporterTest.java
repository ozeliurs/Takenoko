package com.takenoko.stats;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

import com.takenoko.bot.Bot;
import com.takenoko.engine.BotManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BotCSVExporterTest {
    @Nested
    @DisplayName("readData()")
    class ReadData {
        BotCSVExporter botCSVExporter;
        List<String[]> data;

        @BeforeEach
        void setUp() {
            botCSVExporter = new BotCSVExporter("target/csvtests/readDataTest.csv");
            data =
                    List.of(
                            new String[] {"Bot Name", "Wins", "Losses", "Final Score"},
                            new String[] {"Bot 1", "0", "0", "0"},
                            new String[] {"Bot 2", "1", "2", "3"});
        }

        @Test
        @DisplayName("should read data from a CSV file")
        void shouldReadDataFromCSV() {
            botCSVExporter.readData(data);
            assertThat(botCSVExporter.getBotsData()).containsKeys("Bot 1", "Bot 2");
            assertThat(botCSVExporter.getBotsData())
                    .containsEntry("Bot 1", new SingleBotStatistics(0, 0, 0));
            assertThat(botCSVExporter.getBotsData())
                    .containsEntry("Bot 2", new SingleBotStatistics(1, 2, 3));
        }
    }

    @Nested
    @DisplayName("writeData()")
    class WriteData {
        BotCSVExporter botCSVExporter;

        @BeforeEach
        void setUp() {
            BotStatistics botStatistics = new BotStatistics();
            botStatistics.addBotManager(new BotManager(mock(Bot.class), "Bot 1"));

            botCSVExporter = new BotCSVExporter("target/csvtests/writeDataTest.csv");
            botCSVExporter.addStatistics(botStatistics);
        }

        @Test
        @DisplayName("first line should be the header")
        void firstLineShouldBeHeader() {
            assertThat(botCSVExporter.writeData())
                    .contains(new String[] {"Bot Name", "Wins", "Losses", "Final Score"});
        }

        @Test
        @DisplayName("second line should be the data")
        void secondLineShouldBeData() {
            assertThat(botCSVExporter.writeData()).contains(new String[] {"Bot 1", "0", "0", "0"});
        }
    }
}

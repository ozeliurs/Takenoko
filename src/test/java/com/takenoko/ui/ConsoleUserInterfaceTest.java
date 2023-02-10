package com.takenoko.ui;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.jupiter.api.*;

/** Console user interface test */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsoleUserInterfaceTest {
    org.apache.logging.log4j.core.Logger logger;
    ByteArrayOutputStream testOut;
    Appender appender;
    private ConsoleUserInterface consoleUserInterface;

    /** Create a new ConsoleUserInterface with test streams */
    @BeforeEach
    void beforeAllTests() {
        LoggerContext context = LoggerContext.getContext();
        testOut = new ByteArrayOutputStream();
        Configuration config = context.getConfiguration();
        appender =
                WriterAppender.newBuilder()
                        .setTarget(new OutputStreamWriter(testOut))
                        .setName("Test")
                        .setConfiguration(config)
                        .build();
        appender.start();
        logger = context.getLogger("Test");
        logger.addAppender(appender);
        consoleUserInterface = new ConsoleUserInterface(logger);
    }

    /** Restore the original streams */
    @AfterEach
    void tearDown() {
        testOut.reset();
        logger.removeAppender(appender);
        appender.stop();
    }

    /** Test the message display */
    @DisplayName("Display message")
    @Nested
    class DisplayMessage {
        /** Test the message display */
        @Test
        void displayMessage() {
            consoleUserInterface.displayMessage("message");
            assertThat(testOut.toString()).contains("message");
        }

        /** Test an error message display */
        @Test
        void displayErrorMessage() {
            consoleUserInterface.displayError("error message");
            assertThat(testOut.toString()).contains("error message");
        }

        /** Test the end message display */
        @Test
        void displayEndMessage() {
            consoleUserInterface.displayEnd("end message");
            assertThat(testOut.toString()).contains("end message");
        }

        /** Test the stats message display */
        @Test
        void displayStatsMessage() {
            consoleUserInterface.displayStats("stat summary message");
            assertThat(testOut.toString()).contains("stat summary message");
        }

        /** Test the scoreBoard message display */
        @Test
        void displayScoreBoardMessage() {
            consoleUserInterface.displayScoreBoard("scoreBoard message");
            assertThat(testOut.toString()).contains("scoreBoard message");
        }
    }
}

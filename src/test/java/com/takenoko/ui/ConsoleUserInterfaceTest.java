package com.takenoko.ui;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.jupiter.api.*;

/** Console user interface test */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsoleUserInterfaceTest {
    Logger logger;
    ByteArrayOutputStream testOut;
    private ConsoleUserInterface consoleUserInterface;

    /** Create a new ConsoleUserInterface with test streams */
    @BeforeAll
    void beforeAllTests() {
        LoggerContext context = LoggerContext.getContext();
        testOut = new ByteArrayOutputStream();
        Configuration config = context.getConfiguration();
        Appender appender =
                WriterAppender.newBuilder()
                        .setTarget(new OutputStreamWriter(testOut))
                        .setName("Test")
                        .setConfiguration(config)
                        .build();
        appender.start();
        config.getRootLogger().setLevel(Level.ALL);
        config.getRootLogger().addAppender(appender, Level.ALL, null);
        logger = context.getLogger(ConsoleUserInterface.class);
        consoleUserInterface = new ConsoleUserInterface(logger);
    }

    /** Restore the original streams */
    @AfterEach
    void tearDown() {
        testOut.reset();
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
    }
}

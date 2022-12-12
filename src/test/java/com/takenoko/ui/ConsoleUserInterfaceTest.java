package com.takenoko.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

/** Console user interface test */
class ConsoleUserInterfaceTest {
    ConsoleUserInterface consoleUserInterface;
    PrintStream sysOutBackup = System.out;
    PrintStream sysErrBackup = System.err;

    ByteArrayOutputStream testOut;
    ByteArrayOutputStream testErr;

    /** Create a new ConsoleUserInterface with test streams */
    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        testErr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(testErr));
        consoleUserInterface = new ConsoleUserInterface();
    }

    /** Restore the original streams */
    @AfterEach
    void tearDown() {
        System.setOut(sysOutBackup);
        System.setErr(sysErrBackup);
    }

    /** Test the message display */
    @DisplayName("Display message")
    @Nested
    class DisplayMessage {
        /** Test the message display */
        @Test
        void displayMessage() {
            consoleUserInterface.displayMessage("test");
            assertEquals("test" + System.lineSeparator(), testOut.toString());
        }

        /** Test an error message display */
        @Test
        void displayErrorMessage() {
            consoleUserInterface.displayError("test");
            assertEquals("test" + System.lineSeparator(), testErr.toString());
        }
    }
}

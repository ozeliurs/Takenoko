package com.takenoko.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Console user interface
 *
 * <p>This class is used to interact with the user through loggers.
 */
public class ConsoleUserInterface implements UserInterface {
    private final Logger logger;

    /**
     * Create a new ConsoleUserInterface with the given logger
     *
     * @param logger the logger to use
     */
    public ConsoleUserInterface(Logger logger) {
        this.logger = logger;
    }

    /** Create a new ConsoleUserInterface with default logger */
    public ConsoleUserInterface() {
        this(LogManager.getLogger(ConsoleUserInterface.class));
    }

    /**
     * Display a message to logger.info
     *
     * @param message the message to display to logger.info
     */
    public void displayMessage(String message) {
        logger.info(message);
    }

    /**
     * Display a message to logger.error
     *
     * @param message the error message to display to logger.error
     */
    public void displayError(String message) {
        logger.error(message);
    }

    public void displayLineSeparator() {
        logger.info("------------------------------------------------------------");
    }
}

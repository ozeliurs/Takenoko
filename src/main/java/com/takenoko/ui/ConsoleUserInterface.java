package com.takenoko.ui;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Console user interface
 *
 * <p>This class is used to interact with the user through loggers.
 */
public class ConsoleUserInterface implements UserInterface {
    private final Logger logger;
    public static final Level GAMESTATS=Level.forName("GAMESTATS",50);
    public static final Level SCOREBOARD= Level.forName("SCOREBOARD",50);
    public static final Level END=Level.forName("END",40);

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

    @Override
    public void displayDebug(String message) {
        logger.debug(message);
    }

    public void displayStats(String message){ logger.log(GAMESTATS,message);}
    public void displayScoreBoard(String message){ logger.log(SCOREBOARD,message);}
    public void displayEnd(String message){ logger.log(END,message);}

    public void displayLineSeparator() {
        logger.info("------------------------------------------------------------");
    }
}

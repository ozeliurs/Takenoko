package com.takenoko.ui;

import java.io.PrintStream;

/**
 * Console user interface
 *
 * <p>This class is used to interact with the user through streams By default it uses System.out and
 * System.err but it can be used with any other input and output streams by using the constructor
 * with the corresponding parameters
 *
 * <p>
 */
public class ConsoleUserInterface implements UserInterface {
    PrintStream out;
    PrintStream err;

    /**
     * Create a new ConsoleUserInterface with the given streams
     *
     * @param out the output stream
     * @param err the error stream
     */
    public ConsoleUserInterface(PrintStream out, PrintStream err) {
        this.out = out;
        this.err = err;
    }

    /** Create a new ConsoleUserInterface with default streams */
    public ConsoleUserInterface() {
        this(System.out, System.err);
    }

    /**
     * Display a message to the output stream
     *
     * @param message the message to display to the output stream
     */
    public void displayMessage(String message) {
        out.println(message);
    }

    /**
     * Display a message to the error stream
     *
     * @param message the error message to display to the error stream
     */
    public void displayError(String message) {
        err.println(message);
    }
}

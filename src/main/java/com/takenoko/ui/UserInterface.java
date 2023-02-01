package com.takenoko.ui;

/**
 * User interface
 *
 * <p>This interface is the base for all user interfaces used in the application
 *
 * <p>
 */
public interface UserInterface {
    void displayMessage(String message);

    void displayError(String message);

    void displayDebug(String message);
}

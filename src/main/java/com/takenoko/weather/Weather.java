package com.takenoko.weather;

/**
 * Weather modifier for the game, each element has it's ows effect on the game. The weather can be
 * sunny, rainy, cloudy, windy, storm or random.
 */
public enum Weather {
    /**
     * A great sun shines on the bamboo grove. ☀️ The player benefits from an additional action.
     * This action must be different from his two regular actions.
     */
    SUNNY,
    /**
     * A fine rain comes to nourish the young shoots of bamboo. 🌧️ The player can add a section on
     * the plot of his choice of his choice within the limit of 4 sections per bamboo maximum of 4
     * sections per bamboo.
     */
    RAINY,
    /**
     * A refreshing breeze blows through the bamboo grove. 💨 The player may, if he wishes, perform
     * two identical actions during this turn (instead of two different actions).
     */
    WINDY,
    /**
     * The sky rumbles and lightning strikes, scaring the panda. ⚡️ The player can move the panda
     * back to the plot of his choice. To recover from its emotions, the fearful animal eats a
     * section of bamboo. The section is placed on the player's individual board.
     */
    STORM,
    /**
     * Grey clouds are veiling the sky. ☁️ So why not take advantage of this time to do some work?
     * The player can take one of the available facilities from the reserve and place it on the plot
     * of his choice. If there are no buildings available, the player can move a building already in
     * play.
     */
    CLOUDY,
    /**
     * The player chooses which climate condition he wishes to apply this round: sun, rain, wind,
     * storm or clouds.
     */
    RANDOM
}

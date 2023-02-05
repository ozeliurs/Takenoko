package com.takenoko.weather;

/**
 * Weather modifier for the game, each element has it's ows effect on the game. The weather can be
 * sunny, rainy, cloudy, windy, storm or random.
 */
public enum WeatherFactory {
    /**
     * A great sun shines on the bamboo grove. ☀️ The player benefits from an additional action.
     * This action must be different from his two regular actions.
     */
    SUNNY {
        @Override
        public Weather createWeather() {
            return new Sunny();
        }
    },

    /**
     * Gray clouds ☁️ darken the sky. Never mind, it is time to go on and perform some handy work.
     * The player chooses an Improvement chip from those available in the reserve. It can then be
     * placed immediately on a plot or stored on his individual Board (see page 8). If no
     * Improvement is available, the player applies the effect of another climatic condition of his
     * choice (sun, rain, wind or storm).
     */
    CLOUDY {
        @Override
        public Weather createWeather() {
            return new Cloudy();
        }
    },

    /**
     * If he gets the “?” face, the player chooses what conditions they wish to apply this turn:
     * Sun, Rain, Wind, Storm or Clouds.
     */
    QUESTION_MARK {
        @Override
        public Weather createWeather() {
            return new QuestionMark();
        }
    },

    /**
     * A refreshing breeze 💨 blows through the bamboo garden. The player may, but is not required
     * to, take two identical actions in this round (instead of two different actions).
     */
    WINDY {
        @Override
        public Weather createWeather() {
            return new Windy();
        }
    };

    public abstract Weather createWeather();
}

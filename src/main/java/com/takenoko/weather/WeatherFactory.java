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

    CLOUDY {
        @Override
        public Weather createWeather() {
            return new Cloudy();
        }
    },

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

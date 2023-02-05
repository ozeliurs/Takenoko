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
     * A fine rain nourishes the young bamboo shoots. The player may place a Bamboo section on the
     * irrigated plot of his choice, up to a limit of four sections per plot.
     */
    RAINY {
        @Override
        public Weather createWeather() {
            return new Rainy();
        }
    };

    public abstract Weather createWeather();
}

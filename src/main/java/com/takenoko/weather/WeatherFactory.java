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
     * The sky rumbles and lightning strikes, frightening the panda. The player can put the panda on
     * the plot of his choice. To recover from his fear, the shy animal eats a section of bamboo.
     */
    STORM {
        @Override
        public Weather createWeather() {
            return new Storm();
        }
    };

    public abstract Weather createWeather();
}

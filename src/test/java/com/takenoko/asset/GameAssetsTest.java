package com.takenoko.asset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.takenoko.weather.WeatherDice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameAssetsTest {
    private GameAssets gameAssets;
    private WeatherDice weatherDice;

    @BeforeEach
    void setUp() {
        weatherDice = mock(WeatherDice.class);
        gameAssets = new GameAssets(weatherDice);
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When called on itself should return true")
        @SuppressWarnings("EqualsWithItself")
        void EqualsWithItselfIsTrue() {
            assertThat(gameAssets.equals(gameAssets)).isTrue();
        }

        @Test
        @DisplayName("When gameAssets-s are equal, returns true")
        void equals_WhenGameAssets_S_AreEqual_ThenReturnsTrue() {
            GameAssets gameAssets1 = new GameAssets(weatherDice);
            assertThat(gameAssets1).isEqualTo(gameAssets);
        }

        @Test
        @DisplayName("When gameAssets-s are not equal, returns false")
        void equals_WhenGameAssetsAreNotEqual_ThenReturnsFalse() {
            GameAssets gameAssets1 = new GameAssets(mock(WeatherDice.class));
            assertThat(gameAssets).isNotEqualTo(gameAssets1);
        }

        @Test
        @DisplayName("When gameAssets-s is null, returns false")
        void equals_WhenGameAssetsIsNull_ThenReturnsFalse() {
            assertThat(gameAssets).isNotEqualTo(null);
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When gameAssets-s are equal, returns same hash code")
        void hashCode_WhenGameAssets_S_AreEqual_ThenReturnsSameHashCode() {
            GameAssets gameAssets1 = new GameAssets(weatherDice);
            assertThat(gameAssets1).hasSameHashCodeAs(gameAssets);
        }

        @Test
        @DisplayName("When gameAssets-s are not equal, returns different hash code")
        void hashCode_WhenGameAssets_S_AreNotEqual_ThenReturnsDifferentHashCode() {
            GameAssets gameAssets1 = new GameAssets();
            assertThat(gameAssets).doesNotHaveSameHashCodeAs(gameAssets1);
        }
    }

    @Nested
    @DisplayName("Method copy")
    class TestCopy {
        @Test
        @DisplayName("When gameAssets is copied, returns a new gameAssets")
        void copy_WhenGameAssetsIsCopied_ThenReturnsNewGameAssets() {
            GameAssets gameAssets1 = new GameAssets();
            assertThat(gameAssets1.copy()).isNotSameAs(gameAssets1).isEqualTo(gameAssets1);
        }
    }
}

package com.takenoko.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DiceTest {

    @Nested
    @DisplayName("method roll()")
    class Roll {
        @Test
        @DisplayName("should return a number between 0 and 5 when random is between 0 and 5")
        void shouldReturnANumberBetween0And5WhenRandomIsBetween0And5() {
            Random random = mock(Random.class);
            when(random.nextInt(any(Integer.class))).thenReturn(0, 1, 2, 3, 4, 5);
            // Given
            Dice dice = new Dice(6, random);

            // When
            for (int i = 0; i < 6; i++) {
                int roll = dice.roll();

                // Then
                assertThat(roll).isEqualTo(i);
            }
        }
    }

    @Nested
    @DisplayName("method equals()")
    class Equals {
        @Test
        @DisplayName("should return true when the number of sides is the same")
        void shouldReturnTrueWhenTheNumberOfSidesIsTheSameTestEqual() {
            Dice dice1 = new Dice(6);
            Dice dice2 = new Dice(6);

            assertThat(dice1).isEqualTo(dice2);
        }

        @Test
        @DisplayName("should return false when the number of sides is different")
        void shouldReturnFalseWhenTheNumberOfSidesIsDifferent() {
            Dice dice1 = new Dice(6);
            Dice dice2 = new Dice(8);

            assertThat(dice1).isNotEqualTo(dice2);
        }

        @Test
        @DisplayName("should return false when the object is not a dice")
        void shouldReturnFalseWhenTheObjectIsNotADice() {
            Dice dice = new Dice(6);
            Object object = new Object();

            assertThat(dice).isNotEqualTo(object);
        }

        @Test
        @DisplayName("should return false when the object is null")
        void shouldReturnFalseWhenTheObjectIsNull() {
            Dice dice = new Dice(6);

            assertThat(dice).isNotNull();
        }

        @Test
        @DisplayName("should return true when the object is the same")
        void shouldReturnTrueWhenTheObjectIsTheSame() {
            Dice dice = new Dice(6);

            assertThat(dice).isEqualTo(dice);
        }
    }

    @Nested
    @DisplayName("method hashCode()")
    class HashCode {
        @Test
        @DisplayName("should return the same hash code when the number of sides is the same")
        void shouldReturnTheSameHashCodeWhenTheNumberOfSidesIsTheSame() {
            Dice dice1 = new Dice(6);
            Dice dice2 = new Dice(6);

            assertThat(dice1).hasSameHashCodeAs(dice2);
        }

        @Test
        @DisplayName("should return a different hash code when the number of sides is different")
        void shouldReturnADifferentHashCodeWhenTheNumberOfSidesIsDifferent() {
            Dice dice1 = new Dice(6);
            Dice dice2 = new Dice(8);

            assertThat(dice1).doesNotHaveSameHashCodeAs(dice2);
        }
    }

    @Nested
    @DisplayName("method peek()")
    class Peek {
        @Test
        @DisplayName("should return the last roll when the dice is rolled")
        void shouldReturnTheLastRollWhenTheDiceIsRolled() {
            Random random = mock(Random.class);
            when(random.nextInt(any(Integer.class))).thenReturn(0, 1, 2, 3, 4, 5);
            Dice dice = new Dice(6, random);

            for (int i = 0; i < 6; i++) {
                dice.roll();
            }

            assertThat(dice.peek()).isEqualTo(5);
        }
    }
}

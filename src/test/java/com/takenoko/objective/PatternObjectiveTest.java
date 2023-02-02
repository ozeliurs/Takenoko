package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.shape.Pattern;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PatternObjectiveTest {
    @Nested
    @DisplayName("Method verify")
    class TestVerify {
        @Test
        @DisplayName("When board matches Pattern, state is ACHIEVED")
        void verify_WhenBoardHasTwoTilesNextToEachOther_ThenObjectiveStateIsACHIEVED() {
            Pattern pattern = mock(Pattern.class);
            when(pattern.match(new HashMap<>())).thenReturn(List.of(mock(Pattern.class)));
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            patternObjective.verify(mock(Board.class), mock(BotManager.class));
            assertEquals(ObjectiveState.ACHIEVED, patternObjective.getState());
        }

        @Test
        @DisplayName("When board doesn't match Pattern, state is NOT_ACHIEVED")
        void verify_WhenBoardHasTwoTilesNextToEachOther_ThenObjectiveStateIsNOT_ACHIEVED() {
            Pattern pattern = mock(Pattern.class);
            when(pattern.match(new HashMap<>())).thenReturn(List.of());
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            patternObjective.verify(mock(Board.class), mock(BotManager.class));
            assertEquals(ObjectiveState.NOT_ACHIEVED, patternObjective.getState());
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {

        @Test
        @DisplayName("When PatternObjective is compared to itself, it is equal")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenPatternObjectiveIsComparedToItself_ThenItIsEqual() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            assertThat(patternObjective.equals(patternObjective)).isTrue();
        }

        @Test
        @DisplayName("When PatternObjective is compared to null, it is not equal")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenPatternObjectiveIsComparedToNull_ThenItIsNotEqual() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            assertThat(patternObjective.equals(null)).isFalse();
        }

        @Test
        @DisplayName(
                "When PatternObjective is compared to an object of another class, it is not equal")
        void equals_WhenPatternObjectiveIsComparedToAnObjectOfAnotherClass_ThenItIsNotEqual() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            assertThat(patternObjective.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName(
                "When PatternObjective is compared to another PatternObjective with the same"
                        + " Pattern, it is equal")
        void
                equals_WhenPatternObjectiveIsComparedToAnotherPatternObjectiveWithTheSamePattern_ThenItIsEqual() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            PatternObjective patternObjective2 = new PatternObjective(pattern, 0);
            assertThat(patternObjective.equals(patternObjective2)).isTrue();
        }

        @Test
        @DisplayName(
                "When PatternObjective is compared to another PatternObjective with a different"
                        + " Pattern, it is not equal")
        void
                equals_WhenPatternObjectiveIsComparedToAnotherPatternObjectiveWithADifferentPattern_ThenItIsNotEqual() {
            Pattern pattern = mock(Pattern.class);
            Pattern pattern2 = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            PatternObjective patternObjective2 = new PatternObjective(pattern2, 0);
            assertThat(patternObjective.equals(patternObjective2)).isFalse();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When PatternObjective is compared to itself, it has the same hash code")
        void hashCode_WhenPatternObjectiveIsComparedToItself_ThenItHasTheSameHashCode() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            assertThat(patternObjective).hasSameHashCodeAs(patternObjective);
        }

        @Test
        @DisplayName(
                "When PatternObjective is compared to another PatternObjective with the same"
                        + " Pattern, it has the same hash code")
        void
                hashCode_WhenPatternObjectiveIsComparedToAnotherPatternObjectiveWithTheSamePattern_ThenItHasTheSameHashCode() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            PatternObjective patternObjective2 = new PatternObjective(pattern, 0);
            assertThat(patternObjective).hasSameHashCodeAs(patternObjective2);
        }

        @Test
        @DisplayName(
                "When PatternObjective is compared to another PatternObjective with a different"
                        + " Pattern, it has a different hash code")
        void
                hashCode_WhenPatternObjectiveIsComparedToAnotherPatternObjectiveWithADifferentPattern_ThenItHasADifferentHashCode() {
            Pattern pattern = mock(Pattern.class);
            Pattern pattern2 = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            PatternObjective patternObjective2 = new PatternObjective(pattern2, 0);
            assertThat(patternObjective).doesNotHaveSameHashCodeAs(patternObjective2);
        }
    }

    @Nested
    @DisplayName("Method copy")
    class TestCopy {
        @Test
        @DisplayName("When PatternObjective is copied, it is equal to the original")
        void copy_WhenPatternObjectiveIsCopied_ThenItIsEqualToTheOriginal() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            PatternObjective patternObjective2 = patternObjective.copy();
            assertThat(patternObjective).isEqualTo(patternObjective2);
        }
    }

    @Nested
    @DisplayName("Method getCompletion")
    class TestGetCompletion {
        @Test
        @DisplayName("When PatternObjective is called calls getCompletion on Pattern")
        void getCompletion_WhenPatternObjectiveIsCalled_CallsGetCompletionOnPattern() {
            Pattern pattern = mock(Pattern.class);
            PatternObjective patternObjective = new PatternObjective(pattern, 0);
            patternObjective.getCompletion(mock(Board.class), mock(BotManager.class));
            verify(pattern).matchRatio(any());
            // then verify that it returns the same value as the pattern
            when(pattern.matchRatio(any())).thenReturn(1f);
            assertThat(patternObjective.getCompletion(mock(Board.class), mock(BotManager.class)))
                    .isEqualTo(1f);
        }
    }
}

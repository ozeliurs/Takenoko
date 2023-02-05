package com.takenoko.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.Action;
import com.takenoko.actions.bamboo.GrowBambooAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RainyTest {

    @Nested
    @DisplayName("apply")
    class Apply {
        @Test
        @DisplayName("should return GrowBambooAction when bamboo is growable")
        void shouldReturnGrowBambooActionWhenBambooIsGrowable() {
            Board board = mock(Board.class);
            when(board.getGrowablePositions()).thenReturn(List.of(mock(PositionVector.class)));
            Rainy rainy = new Rainy();

            List<Class<? extends Action>> actions = rainy.apply(board, mock(BotManager.class));

            assertThat(actions).containsExactly(GrowBambooAction.class);
        }

        @Test
        @DisplayName("should return empty list when bamboo is not growable")
        void shouldReturnEmptyListWhenBambooIsNotGrowable() {
            Board board = mock(Board.class);
            when(board.getGrowablePositions()).thenReturn(List.of());
            Rainy rainy = new Rainy();

            List<Class<? extends Action>> actions = rainy.apply(board, mock(BotManager.class));

            assertThat(actions).isEmpty();
        }
    }
}

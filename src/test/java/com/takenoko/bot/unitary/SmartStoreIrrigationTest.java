package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SmartStoreIrrigationTest {
    @Nested
    @DisplayName("fillAction")
    class FillAction {
        @DisplayName("should add StoreIrrigationInInventoryAction to actions")
        @Test
        void shouldAddStoreIrrigationInInventoryActionToActions() {
            SmartStoreIrrigation smartStoreIrrigation = spy(new SmartStoreIrrigation());
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
            History history = mock(History.class);

            when(botState.getAvailableActions()).thenReturn(List.of(DrawIrrigationAction.class));

            smartStoreIrrigation.fillAction(board, botState, history);

            assertThat(smartStoreIrrigation.chooseAction(board, botState, history))
                    .isInstanceOf(DrawIrrigationAction.class);
        }
    }
}

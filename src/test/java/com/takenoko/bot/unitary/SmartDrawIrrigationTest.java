package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SmartDrawIrrigationTest {
    @Nested
    @DisplayName("method fillAction")
    class FillAction {
        @Test
        @Disabled
        @DisplayName(
                "Should draw an irrigation when lumber in inventory lower than arbitrary limit")
        void drawIrrigationWhenLumberLow() {
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
            when(botState.getInventory().getIrrigationChannelsCount()).thenReturn(2);

            SmartDrawIrrigation smartDrawIrrigation = new SmartDrawIrrigation();
            smartDrawIrrigation.fillAction(board, botState, mock(History.class));

            assertThat(smartDrawIrrigation.keySet())
                    .containsExactlyInAnyOrder(new DrawIrrigationAction());
        }
    }
}

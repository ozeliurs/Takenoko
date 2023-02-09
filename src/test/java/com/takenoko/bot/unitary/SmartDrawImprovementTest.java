package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.improvement.DrawImprovementAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.ImprovementType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SmartDrawImprovementTest {

    @Nested
    @DisplayName("method fillAction")
    class FillAction {
        @Test
        @DisplayName(
                "Should draw an improvement when there is less than improvement in the inventory")
        void shouldDrawAnImprovementWhenThereIsLessThanImprovementInTheInventory() {
            SmartDrawImprovement smartDrawImprovement =
                    new SmartDrawImprovement(
                            Map.of(
                                    ImprovementType.ENCLOSURE, 2,
                                    ImprovementType.FERTILIZER, 3,
                                    ImprovementType.WATERSHED, 5));

            BotState botState = mock(BotState.class);
            when(botState.getInventory()).thenReturn(mock(Inventory.class));
            when(botState.getInventory().getImprovementCount(ImprovementType.ENCLOSURE))
                    .thenReturn(2);
            when(botState.getInventory().getImprovementCount(ImprovementType.FERTILIZER))
                    .thenReturn(2);
            when(botState.getInventory().getImprovementCount(ImprovementType.WATERSHED))
                    .thenReturn(4);

            smartDrawImprovement.fillAction(mock(Board.class), botState, mock(History.class));

            assertThat(smartDrawImprovement.keySet())
                    .containsExactlyInAnyOrder(
                            new DrawImprovementAction(ImprovementType.FERTILIZER),
                            new DrawImprovementAction(ImprovementType.WATERSHED));
        }
    }
}

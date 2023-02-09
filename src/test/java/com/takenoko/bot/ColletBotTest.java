package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.Action;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ColletBotTest {

    @Nested
    @DisplayName("Integration tests")
    class IntegrationTests {
        @Test
        @DisplayName(
                "The first two movements of the bot should therefore be to take an objective card"
                        + " and take an irrigation canal.")
        void
                theFirstTwoMovementsOfTheBotShouldThereforeBeToTakeAnObjectiveCardAndTakeAnIrrigationCanal() {
            Board board = new Board();
            BotState botState = spy(BotState.class);
            History history = mock(History.class);
            ColletBot colletBot = new ColletBot();

            when(botState.getAvailableActions())
                    .thenReturn(
                            List.of(
                                    DrawTileAction.class,
                                    DrawIrrigationAction.class,
                                    DrawObjectiveAction.class));

            Action action = colletBot.chooseAction(board, botState, history);

            assertThat(action).isInstanceOf(DrawObjectiveAction.class);

            when(botState.getAvailableActions())
                    .thenReturn(List.of(DrawTileAction.class, DrawIrrigationAction.class));

            action = colletBot.chooseAction(board, botState, history);

            assertThat(action).isInstanceOf(DrawIrrigationAction.class);
        }
    }
}

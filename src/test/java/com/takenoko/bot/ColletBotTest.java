package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.EmperorObjective;
import com.takenoko.objective.SingleGardenerObjective;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.WeatherFactory;
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

        @Test
        @DisplayName(
                "He collects as many bamboos as possible, even if he has no cards with the"
                        + " corresponding color")
        void heCollectsAsManyBamboosAsPossibleEvenIfHeHasNoCardsWithTheCorrespondingColor() {
            Board board = spy(new Board());

            BotState botState = mock(BotState.class);
            when(botState.getNotAchievedObjectives()).thenReturn(List.of(mock(SingleGardenerObjective.class)));
            when(botState.getInventory()).thenReturn(new Inventory());

            when(board.getPandaPossibleMoves())
                    .thenReturn(List.of(mock(PositionVector.class), mock(PositionVector.class)));

            when(board.isBambooEatableAt(any())).thenReturn(true);

            History history = mock(History.class);
            ColletBot colletBot = new ColletBot();

            when(botState.getAvailableActions())
                    .thenReturn(
                            List.of(
                                    MovePandaAction.class,
                                    MoveGardenerAction.class,
                                    DrawTileAction.class));

            Action action = colletBot.chooseAction(board, botState, history);

            assertThat(action).isInstanceOf(MovePandaAction.class);
        }

        @Test
        @DisplayName(
                "When the bot rolls the weather ? in the EARLY_GAME phase, he takes the cloudy"
                        + " meteo")
        void whenTheBotRollsTheWeatherInTheEarlyGamePhaseHeTakesTheCloudyMeteo() {
            Board board = spy(new Board());
            BotState botState = spy(BotState.class);
            History history = new History();
            ColletBot colletBot = new ColletBot();

            when(botState.getAvailableActions())
                    .thenReturn(List.of(ChooseAndApplyWeatherAction.class));

            Action action = colletBot.chooseAction(board, botState, history);
            assertThat(action)
                    .isEqualTo(
                            new ChooseAndApplyWeatherAction(WeatherFactory.CLOUDY.createWeather()));
        }
    }
}

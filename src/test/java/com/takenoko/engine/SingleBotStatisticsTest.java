package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.actions.Action;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.Objective;
import com.takenoko.objective.ObjectiveTypes;
import com.takenoko.stats.SingleBotStatistics;
import com.takenoko.weather.Weather;
import org.junit.jupiter.api.*;

class SingleBotStatisticsTest {
    private SingleBotStatistics singleBotStatistics;
    private static final String wins = "\t -Wins : ";
    private static final String losses = "\t -Losses : ";
    private static final String irrigationsPlaced = "\t -Irrigations Placed : ";
    private static final String finalScore = "\t -Final Score : ";

    @BeforeEach
    void setUp() {
        singleBotStatistics = new SingleBotStatistics();
    }

    @AfterEach
    void tearDown() {
        singleBotStatistics = null;
    }

    @Test
    @DisplayName("Constructor Test")
    void TestConstructor() {
        // When first created, a SingleBotStatistics' numericStatistics attribute should have win,
        // losses, irrigationPlaced and finalScore
        assertThat(singleBotStatistics.getNumericStats())
                .containsKeys(wins, losses, irrigationsPlaced, finalScore);
    }

    @Nested
    @DisplayName("NumericStats Incrementation Tests")
    class TestIncrementation {
        @Test
        @DisplayName("Test win incrementation")
        void testWinIncrementation() {
            int oldValue = singleBotStatistics.getNumericStats().get(wins);
            singleBotStatistics.incrementWins();
            assertThat(singleBotStatistics.getNumericStats()).containsEntry(wins, oldValue + 1);
        }

        @Test
        @DisplayName("Test loss incrementation")
        void testLossIncrementation() {
            int oldValue = singleBotStatistics.getNumericStats().get(losses);
            singleBotStatistics.incrementLosses();
            assertThat(singleBotStatistics.getNumericStats()).containsEntry(losses, oldValue + 1);
        }

        @Test
        @DisplayName("Test irrigation incrementation")
        void testIrrigationIncrementation() {
            int oldValue = singleBotStatistics.getNumericStats().get(irrigationsPlaced);
            singleBotStatistics.incrementIrrigationsPlaced();
            assertThat(singleBotStatistics.getNumericStats())
                    .containsEntry(irrigationsPlaced, oldValue + 1);
        }

        @Test
        @DisplayName("Test updateScore")
        void testUpdateScore() {
            int oldValue = singleBotStatistics.getNumericStats().get(finalScore);
            singleBotStatistics.updateScore(5);
            assertThat(singleBotStatistics.getNumericStats())
                    .containsEntry(finalScore, oldValue + 5);
        }
    }

    @Nested
    @DisplayName("Test Update Objectives Redeemed")
    class TestUpdateObjectivesRedeemed {
        @Test
        @DisplayName("when the redeemed objective is of new type, should add new entry")
        void whenTheUpdatedObjectiveIsNew_shouldAddNewEntry() {
            Objective objective = mock(Objective.class);
            when(objective.getType()).thenReturn(ObjectiveTypes.PANDA);
            int oldSize = singleBotStatistics.getObjectivesRedeemed().size();
            singleBotStatistics.updateObjectivesRedeemed(objective);
            assertThat(singleBotStatistics.getObjectivesRedeemed()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the redeemed objective isn't of new type, shouldn't add new entry")
        void whenTheUpdatedObjectiveIsntNew_shouldntAddNewEntry() {
            Objective objective = mock(Objective.class);
            when(objective.getType()).thenReturn(ObjectiveTypes.PANDA);
            singleBotStatistics.updateObjectivesRedeemed(objective);
            int oldSize = singleBotStatistics.getObjectivesRedeemed().size();
            singleBotStatistics.updateObjectivesRedeemed(objective);
            assertThat(singleBotStatistics.getObjectivesRedeemed()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of objectives for the right type")
        void shouldIncrementNumberOfObjectivesForTheRightType() {
            Objective objective = mock(Objective.class);
            when(objective.getType()).thenReturn(ObjectiveTypes.PANDA);
            singleBotStatistics.updateObjectivesRedeemed(objective);
            int oldValue = singleBotStatistics.getObjectivesRedeemed().get(ObjectiveTypes.PANDA);
            singleBotStatistics.updateObjectivesRedeemed(objective);
            assertThat(singleBotStatistics.getObjectivesRedeemed())
                    .containsEntry(ObjectiveTypes.PANDA, oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updateObjectivesRedeemed(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test Update Eaten Bamboo Counter")
    class TestUpdateEatenBambooCounter {
        @Test
        @DisplayName("when a bamboo of new color is eaten, should add new entry")
        void whenTheEatenBambooIsOfNewColor_shouldAddNewEntry() {
            TileColor tileColor = mock(TileColor.class);
            int oldSize = singleBotStatistics.getBambooCounter().size();
            singleBotStatistics.updateEatenBambooCounter(tileColor);
            assertThat(singleBotStatistics.getBambooCounter()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the bamboo eaten isn't of new color, shouldn't add new entry")
        void whenTheEatenBambooIsntOfNewColor_shouldntAddNewEntry() {
            TileColor tileColor = mock(TileColor.class);
            singleBotStatistics.updateEatenBambooCounter(tileColor);
            int oldSize = singleBotStatistics.getBambooCounter().size();
            singleBotStatistics.updateEatenBambooCounter(tileColor);
            assertThat(singleBotStatistics.getBambooCounter()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of bamboos eaten for the right color")
        void shouldIncrementNumberOfBamboosEatenForTheRightColor() {
            TileColor tileColor = mock(TileColor.class);
            singleBotStatistics.updateEatenBambooCounter(tileColor);
            int oldValue = singleBotStatistics.getBambooCounter().get(tileColor).getLeft();
            singleBotStatistics.updateEatenBambooCounter(tileColor);
            assertThat(singleBotStatistics.getBambooCounter().get(tileColor).getLeft())
                    .isEqualTo(oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updateEatenBambooCounter(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test Update Planted Bamboo Counter")
    class TestUpdatePlantedBamboo {
        @Test
        @DisplayName("when a bamboo of new color is planted, should add new entry")
        void whenThePlantedBambooIsOfNewColor_shouldAddNewEntry() {
            TileColor tileColor = mock(TileColor.class);
            int oldSize = singleBotStatistics.getBambooCounter().size();
            singleBotStatistics.updatePlantedBambooCounter(tileColor, 1);
            assertThat(singleBotStatistics.getBambooCounter()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the bamboo planted isn't of new color, shouldn't add new entry")
        void whenThePlantedBambooIsntOfNewColor_shouldntAddNewEntry() {
            TileColor tileColor = mock(TileColor.class);
            singleBotStatistics.updatePlantedBambooCounter(tileColor, 1);
            int oldSize = singleBotStatistics.getBambooCounter().size();
            singleBotStatistics.updatePlantedBambooCounter(tileColor, 1);
            assertThat(singleBotStatistics.getBambooCounter()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of bamboos planted for the right color")
        void shouldIncrementNumberOfBamboosPlantedForTheRightColor() {
            TileColor tileColor = mock(TileColor.class);
            singleBotStatistics.updatePlantedBambooCounter(tileColor, 1);
            int oldValue = singleBotStatistics.getBambooCounter().get(tileColor).getRight();
            singleBotStatistics.updatePlantedBambooCounter(tileColor, 1);
            assertThat(singleBotStatistics.getBambooCounter().get(tileColor).getRight())
                    .isEqualTo(oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updatePlantedBambooCounter(null, 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test Update Tiles Placed Counter")
    class TestUpdateTilesPlacedCounter {
        @Test
        @DisplayName("when the placed tile is of new type, should add new entry")
        void whenThePlacedTileIsNew_shouldAddNewEntry() {
            TileColor tileColor = mock(TileColor.class);
            int oldSize = singleBotStatistics.getTilesPlaced().size();
            singleBotStatistics.updateTilesPlacedCounter(tileColor);
            assertThat(singleBotStatistics.getTilesPlaced()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the placed tile isn't of new color, shouldn't add new entry")
        void whenThePlacedTileIsntOfNewColor_shouldntAddNewEntry() {
            TileColor tileColor = mock(TileColor.class);
            singleBotStatistics.updateTilesPlacedCounter(tileColor);
            int oldSize = singleBotStatistics.getBambooCounter().size();
            singleBotStatistics.updateTilesPlacedCounter(tileColor);
            assertThat(singleBotStatistics.getTilesPlaced()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("should increment number of placed tiles for the right color")
        void shouldIncrementNumberOfTilesPlacedForTheRightColor() {
            TileColor tileColor = mock(TileColor.class);
            singleBotStatistics.updateTilesPlacedCounter(tileColor);
            int oldValue = singleBotStatistics.getTilesPlaced().get(tileColor);
            singleBotStatistics.updateTilesPlacedCounter(tileColor);
            assertThat(singleBotStatistics.getTilesPlaced()).containsEntry(tileColor, oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updateTilesPlacedCounter(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test Update Weathers Rolled")
    class TestUpdateWeathersRolled {
        @Test
        @DisplayName("when a weather of new type is rolled, should add new entry")
        void whenTheRolledWeatherIsOfNewtype_shouldAddNewEntry() {
            String weather = mock(Weather.class).toString();
            int oldSize = singleBotStatistics.getWeathers().size();
            singleBotStatistics.updateWeathersRolled(weather);
            assertThat(singleBotStatistics.getWeathers()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the rolled weather isn't of new type, shouldn't add new entry")
        void whenTheRolledWeatherIsntOfNewType_shouldntAddNewEntry() {
            String weather = mock(Weather.class).toString();
            singleBotStatistics.updateWeathersRolled(weather);
            int oldSize = singleBotStatistics.getWeathers().size();
            singleBotStatistics.updateWeathersRolled(weather);
            assertThat(singleBotStatistics.getWeathers()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of weathers rolled for the right color")
        void shouldIncrementNumberOfRolledWeathersForTheRightType() {
            String weather = "Sunny";
            singleBotStatistics.updateWeathersRolled(weather);
            int oldValue = singleBotStatistics.getWeathers().get(weather).getRight();
            singleBotStatistics.updateWeathersRolled(weather);
            assertThat(singleBotStatistics.getWeathers().get(weather).getRight())
                    .isEqualTo(oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updateWeathersRolled(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test Update Weathers Applied")
    class TestUpdateWeathersApplied {
        @Test
        @DisplayName("when a weather of new type is applied, should add new entry")
        void whenTheAppliedWeatherIsOfNewtype_shouldAddNewEntry() {
            String weather = mock(Weather.class).toString();
            int oldSize = singleBotStatistics.getWeathers().size();
            singleBotStatistics.updateWeathersApplied(weather);
            assertThat(singleBotStatistics.getWeathers()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the applied weather isn't of new type, shouldn't add new entry")
        void whenTheAppliedWeatherIsntOfNewType_shouldntAddNewEntry() {
            String weather = mock(Weather.class).toString();
            singleBotStatistics.updateWeathersApplied(weather);
            int oldSize = singleBotStatistics.getWeathers().size();
            singleBotStatistics.updateWeathersApplied(weather);
            assertThat(singleBotStatistics.getWeathers()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of weathers applied for the right color")
        void shouldIncrementNumberOfAppliedWeathersForTheRightType() {
            String weather = "Sunny";
            singleBotStatistics.updateWeathersApplied(weather);
            int oldValue = singleBotStatistics.getWeathers().get(weather).getLeft();
            singleBotStatistics.updateWeathersApplied(weather);
            assertThat(singleBotStatistics.getWeathers().get(weather).getLeft())
                    .isEqualTo(oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updateWeathersApplied(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test Update Actions")
    class TestUpdateActions {
        @Test
        @DisplayName("when the updated action is of new type, should add new entry")
        void whenTheUpdatedActionIsOfNewtype_shouldAddNewEntry() {
            String action = mock(Action.class).toString();
            int oldSize = singleBotStatistics.getActions().size();
            singleBotStatistics.updateActions(action);
            assertThat(singleBotStatistics.getActions()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the updated action isn't of new type, shouldn't add new entry")
        void whenTheUpdatedActionIsntOfNewType_shouldntAddNewEntry() {
            String action = mock(Action.class).toString();
            singleBotStatistics.updateActions(action);
            int oldSize = singleBotStatistics.getActions().size();
            singleBotStatistics.updateActions(action);
            assertThat(singleBotStatistics.getActions()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of actions for the right type")
        void shouldIncrementNumberOfActionsForTheRightType() {
            String action = mock(Action.class).toString();
            singleBotStatistics.updateActions(action);
            int oldValue = singleBotStatistics.getActions().get(action);
            singleBotStatistics.updateActions(action);
            assertThat(singleBotStatistics.getActions()).containsEntry(action, oldValue + 1);
        }

        @Test
        @DisplayName("total number of actions should be incremented")
        void totalNbOfActions_shouldBeIncremented() {
            int oldValue = singleBotStatistics.getTotalNbOfAction();
            String action = mock(Action.class).toString();
            singleBotStatistics.updateActions(action);
            assertThat(singleBotStatistics.getTotalNbOfAction()).isEqualTo(oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> singleBotStatistics.updateActions(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}

package com.takenoko.bot.utils;

import static com.takenoko.engine.GameEngine.DEFAULT_NUMBER_OF_ROUNDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.bot.GeneralTacticBot;
import com.takenoko.bot.RushPandaBot;
import com.takenoko.engine.*;
import com.takenoko.objective.PandaObjective;
import com.takenoko.stats.BotStatistics;
import com.takenoko.stats.SingleBotStatistics;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HistoryAnalysisTest {

    private History history;
    private BotManager botManager;
    private BotManager botManager2;

    @BeforeEach
    void setUp() {
        history = new History();
        botManager = new BotManager();
        HistoryItem historyItem4Points = mock(HistoryItem.class);
        PandaObjective pandaObjective = mock(PandaObjective.class);
        TurnHistory turnHistory = new TurnHistory();

        when(pandaObjective.getPoints()).thenReturn(2);
        when(historyItem4Points.redeemedObjectives())
                .thenReturn(List.of(pandaObjective, pandaObjective));
        turnHistory.add(historyItem4Points);
        history.addBotManager(botManager);
        history.setCurrentBotManagerUUID(botManager.getUniqueID());
        history.addTurnHistory(botManager, turnHistory);

        botManager2 = new BotManager();
        HistoryItem historyItem2Points = mock(HistoryItem.class);
        TurnHistory turnHistory2 = new TurnHistory();
        when(historyItem2Points.redeemedObjectives()).thenReturn(List.of(pandaObjective));
        turnHistory2.add(historyItem2Points);
        history.addBotManager(botManager2);
        history.setCurrentBotManagerUUID(botManager2.getUniqueID());
        history.addTurnHistory(botManager2, turnHistory2);
    }

    @Nested
    @DisplayName("Method getCurrentBotScores()")
    class GetCurrentBotScores {
        @Test
        @DisplayName("Should return a map of the current scores of each bot")
        void shouldReturnMapOfCurrentScores() {

            assertThat(HistoryAnalysis.getCurrentBotScores(history))
                    .hasSize(2)
                    .containsEntry(botManager.getUniqueID(), 4)
                    .containsEntry(botManager2.getUniqueID(), 2);
        }
    }

    @Nested
    @DisplayName("Method getMaxCurrentBotScore()")
    class GetMaxCurrentBotScore {
        @Test
        @DisplayName("Should return the maximum score of all the bots")
        void shouldReturnMaxScore() {
            assertThat(HistoryAnalysis.getMaxCurrentBotScore(history)).isEqualTo(4);
        }
    }

    @Nested
    @DisplayName("Method getGameProgress()")
    class GetGameProgress {

        private PandaObjective pandaObjective;
        private HistoryItem historyItem1;
        private HistoryItem historyItem2;

        @BeforeEach
        void setUp() {
            history = new History();
            botManager = new BotManager();
            historyItem1 = mock(HistoryItem.class);
            pandaObjective = mock(PandaObjective.class);
            TurnHistory turnHistory = new TurnHistory();

            when(pandaObjective.getPoints()).thenReturn(2);
            when(historyItem1.redeemedObjectives())
                    .thenReturn(List.of(pandaObjective, pandaObjective));
            turnHistory.add(historyItem1);
            history.addBotManager(botManager);
            history.setCurrentBotManagerUUID(botManager.getUniqueID());
            history.addTurnHistory(botManager, turnHistory);

            botManager2 = new BotManager();
            historyItem2 = mock(HistoryItem.class);
            TurnHistory turnHistory2 = new TurnHistory();
            when(historyItem2.redeemedObjectives()).thenReturn(List.of(pandaObjective));
            turnHistory2.add(historyItem2);
            history.addBotManager(botManager2);
            history.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history.addTurnHistory(botManager2, turnHistory2);
        }

        @Test
        @DisplayName("Should return Early Game when it is early game")
        void shouldReturnEarlyGame() {
            when(historyItem1.redeemedObjectives()).thenReturn(List.of(pandaObjective));
            when(historyItem2.redeemedObjectives()).thenReturn(List.of(pandaObjective));
            assertThat(HistoryAnalysis.getGameProgress(history)).isEqualTo(GameProgress.EARLY_GAME);
        }

        @Test
        @DisplayName("Should return Mid Game when it is mid game")
        void shouldReturnMidGame() {
            when(historyItem1.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            when(historyItem2.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            assertThat(HistoryAnalysis.getGameProgress(history)).isEqualTo(GameProgress.MID_GAME);
        }

        @Test
        @DisplayName("Should return Late Game when it is late game")
        void shouldReturnLateGame() {
            when(historyItem1.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            when(historyItem2.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            assertThat(HistoryAnalysis.getGameProgress(history)).isEqualTo(GameProgress.LATE_GAME);
        }
    }

    @Nested
    @DisplayName("Method analyzeRushPanda()")
    class AnalyzeRushPanda {
        MovePandaAction movePandaAction = mock(MovePandaAction.class);
        MoveGardenerAction moveGardenerAction = mock(MoveGardenerAction.class);

        @Test
        @DisplayName("Should return true when the bot has rushed a panda")
        void shouldReturnTrueWhenBotHasRushedPanda() {
            history = new History();
            botManager = new BotManager();
            ForcedMovePandaAction forcedMovePandaAction = mock(ForcedMovePandaAction.class);
            history.addBotManager(botManager);
            history.setCurrentBotManagerUUID(botManager.getUniqueID());
            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(forcedMovePandaAction, List.of()),
                            new HistoryItem(movePandaAction, List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(mock(Action.class), List.of()),
                            new HistoryItem(mock(Action.class), List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(forcedMovePandaAction, List.of()),
                            new HistoryItem(movePandaAction, List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(forcedMovePandaAction, List.of()),
                            new HistoryItem(movePandaAction, List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            assertThat(HistoryAnalysis.analyzeRushPanda(history, 0.74))
                    .containsEntry(botManager.getUniqueID(), true);
        }

        @Test
        @DisplayName("Should return false when the bot has not rushed a panda")
        void shouldReturnFalseWhenBotHasNotRushedPanda() {
            history = new History();
            botManager = new BotManager();
            ForcedMovePandaAction forcedMovePandaAction = mock(ForcedMovePandaAction.class);
            history.addBotManager(botManager);
            history.setCurrentBotManagerUUID(botManager.getUniqueID());
            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(forcedMovePandaAction, List.of()),
                            new HistoryItem(movePandaAction, List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(mock(Action.class), List.of()),
                            new HistoryItem(mock(Action.class), List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(forcedMovePandaAction, List.of()),
                            new HistoryItem(movePandaAction, List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            history.addTurnHistory(
                    botManager,
                    new TurnHistory(
                            new HistoryItem(mock(Action.class), List.of()),
                            new HistoryItem(mock(Action.class), List.of()),
                            new HistoryItem(moveGardenerAction, List.of())));

            assertThat(HistoryAnalysis.analyzeRushPanda(history))
                    .containsEntry(botManager.getUniqueID(), false);
        }
    }

    @Nested
    @DisplayName("Integration tests")
    class IntegrationTest {
        @Test
        @DisplayName("Should return the correct analysis")
        void test1() {
            History history = new History();
            BotManager rushPandaBotManager =
                    new BotManager(
                            new ConsoleUserInterface(),
                            "Rush Panda",
                            new RushPandaBot(),
                            new BotState(),
                            mock(SingleBotStatistics.class));

            GameEngine gameEngine =
                    new GameEngine(
                            DEFAULT_NUMBER_OF_ROUNDS,
                            new Board(),
                            new ConsoleUserInterface(),
                            GameState.INITIALIZED,
                            new ArrayList<>(
                                    List.of(
                                            new BotManager(
                                                    new ConsoleUserInterface(),
                                                    "Joe",
                                                    new GeneralTacticBot(),
                                                    new BotState(),
                                                    mock(SingleBotStatistics.class)),
                                            rushPandaBotManager)),
                            new Scoreboard(),
                            new BotStatistics(),
                            history);
            gameEngine.newGame();
            gameEngine.startGame();
            gameEngine.playGame();
            assertThat(HistoryAnalysis.analyzeRushPanda(history))
                    .containsEntry(rushPandaBotManager.getUniqueID(), true);
        }
    }
}

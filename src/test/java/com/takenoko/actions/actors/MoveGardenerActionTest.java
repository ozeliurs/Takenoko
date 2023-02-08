package com.takenoko.actions.actors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.SingleBotStatistics;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MoveGardenerActionTest {

    private MoveGardenerAction moveGardenerAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        moveGardenerAction = new MoveGardenerAction(new PositionVector(-1, 0, 1));
        botManager = mock(BotManager.class);
        board = mock(Board.class);
        when(board.moveGardener(any()))
                .thenReturn(Map.of(new PositionVector(-1, 0, 1), new LayerBambooStack(1)));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the gardener plant a bamboo")
        void shouldMoveTheGardenerPlantBamboo() {
            when(board.getGardenerPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(botManager.getName()).thenReturn("Joe");
            when(botManager.getSingleBotStatistics()).thenReturn(mock(SingleBotStatistics.class));
            when(board.isIrrigatedAt(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            moveGardenerAction.execute(board, botManager);
            verify(board).moveGardener(new PositionVector(-1, 0, 1));
        }
    }

    @Nested
    @DisplayName("Method canBePlayed()")
    class TestCanBePlayed {
        @Test
        @DisplayName("should return true if the gardener can move")
        void shouldReturnTrueIfGardenerCanMove() {
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            tiles.put(new PositionVector(1, 0, -1), new Tile());
            when(board.getTilesWithoutPond()).thenReturn(tiles);
            assertThat(MoveGardenerAction.canBePlayed(board)).isTrue();
        }

        @Test
        @DisplayName("should return false if the gardener cant move")
        void shouldReturnFalseIfGardenerCantMove() {
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            when(board.getTilesWithoutPond()).thenReturn(tiles);
            assertThat(MoveGardenerAction.canBePlayed(board)).isFalse();
        }
    }
}

package com.takenoko.actors.panda;

import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.bamboo.BambooStack;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovePandaActionTest {

    private MovePandaAction movePandaAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        movePandaAction = new MovePandaAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        board = mock(Board.class);
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the panda")
        void shouldMoveThePanda() {
            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(board.getBambooAt(any())).thenReturn(new BambooStack(0));
            when(board.getPanda()).thenReturn(mock(Panda.class));
            movePandaAction.execute(board, botManager);
            verify(board.getPanda()).move(new PositionVector(-1, 0, 1));
        }

        @Test
        @DisplayName("should move panda and eat bamboo")
        void shouldMovePandaAndEatBamboo() {
            when(board.getPandaPosition())
                    .thenReturn(
                            (new PositionVector(0, 0, 0).add(new PositionVector(-1, 0, 1)))
                                    .toPositionVector());
            when(board.getBambooAt(any())).thenReturn(new BambooStack(1));
            when(board.getPanda()).thenReturn(mock(Panda.class));
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            BambooLayer bambooLayer = mock(BambooLayer.class);
            when(board.getLayerManager().getBambooLayer()).thenReturn(bambooLayer);
            movePandaAction.execute(board, botManager);
            verify(board.getPanda()).move(new PositionVector(-1, 0, 1));
            verify(board).getBambooAt(new PositionVector(-1, 0, 1));
            verify(bambooLayer).removeBamboo(new PositionVector(-1, 0, 1));
        }
    }
}

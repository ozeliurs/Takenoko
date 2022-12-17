package com.takenoko.actors.panda;

import static org.mockito.Mockito.*;

import com.takenoko.actors.ActorsManager;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.player.Bot;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovePandaActionTest {

    private MovePandaAction movePandaAction;
    private BotManager botManager;
    private Board board;

    private Panda panda;

    @BeforeEach
    void setUp() {
        movePandaAction = new MovePandaAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        panda = mock(Panda.class);
        ActorsManager actorsManager = mock(ActorsManager.class);
        when(actorsManager.getPanda()).thenReturn(panda);
        board = mock(Board.class);
        when(board.getActorsManager()).thenReturn(actorsManager);
        LayerManager layerManager = mock(LayerManager.class);
        BambooLayer bambooLayer = mock(BambooLayer.class);
        when(layerManager.getBambooLayer()).thenReturn(bambooLayer);
        when(board.getLayerManager()).thenReturn(layerManager);
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the panda")
        void shouldMoveThePanda() {
            movePandaAction.execute(board, botManager);
            verify(panda).move(new PositionVector(-1, 0, 1));
        }
    }
}

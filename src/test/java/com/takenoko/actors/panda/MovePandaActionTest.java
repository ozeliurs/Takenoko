package com.takenoko.actors.panda;

import static org.mockito.Mockito.*;

import com.takenoko.actors.ActorsManager;
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

    private Panda panda;
    private BambooLayer bambooLayer;

    @BeforeEach
    void setUp() {
        movePandaAction = new MovePandaAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        panda = mock(Panda.class);
        ActorsManager actorsManager = mock(ActorsManager.class);
        board = mock(Board.class);
        LayerManager layerManager = mock(LayerManager.class);
        bambooLayer = mock(BambooLayer.class);
        when(board.getActorsManager()).thenReturn(actorsManager);
        when(actorsManager.getPanda()).thenReturn(panda);
        when(layerManager.getBambooLayer()).thenReturn(bambooLayer);
        when(board.getLayerManager()).thenReturn(layerManager);
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the panda")
        void shouldMoveThePanda() {
            when(panda.getPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(bambooLayer.getBambooAt(any())).thenReturn(new BambooStack(0));
            movePandaAction.execute(board, botManager);
            verify(panda).move(new PositionVector(-1, 0, 1));
        }

        @Test
        @DisplayName("should move panda and eat bamboo")
        void shouldMovePandaAndEatBamboo() {
            when(panda.getPosition())
                    .thenReturn(new PositionVector(0, 0, 0).add(new PositionVector(-1, 0, 1)));
            when(bambooLayer.getBambooAt(any())).thenReturn(new BambooStack(1));
            movePandaAction.execute(board, botManager);
            verify(panda).move(new PositionVector(-1, 0, 1));
            verify(bambooLayer).getBambooAt(new PositionVector(-1, 0, 1));
            verify(bambooLayer).removeBamboo(new PositionVector(-1, 0, 1));
        }
    }
}

package com.takenoko.bot.utils.pathfinding.panda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.PandaObjective;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PandaPathfindingTest {

    @Nested
    class GetPandaMoves {
        @Test
        void getPandaMoves() {
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);

            when(botState.getNotAchievedObjectives())
                    .thenReturn(
                            List.of(
                                    new PandaObjective(
                                            Map.of(
                                                    TileColor.GREEN,
                                                    1,
                                                    TileColor.YELLOW,
                                                    1,
                                                    TileColor.PINK,
                                                    1),
                                            100)));
            when(botState.getInventory()).thenReturn(mock(Inventory.class));
            when(botState.getInventory().getBambooCount(any())).thenReturn(0);

            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));

            Map<PositionVector, Tile> map =
                    Map.of(
                            new PositionVector(0, 0, 0), new Pond(),
                            new PositionVector(0, 1, -1), new Tile(TileColor.GREEN),
                            new PositionVector(0, 2, -2), new Tile(TileColor.YELLOW),
                            new PositionVector(0, 3, -3), new Tile(TileColor.PINK));

            when(board.getPandaPossibleMoves())
                    .thenReturn(
                            List.of(
                                    new PositionVector(0, 1, -1),
                                    new PositionVector(0, 2, -2),
                                    new PositionVector(0, 3, -3)));
            when(board.getTiles()).thenReturn(map);

            for (PositionVector positionVector : map.keySet()) {
                when(board.isBambooEatableAt(positionVector)).thenReturn(true);
            }

            Map<PandaObjective, List<PositionVector>> completionMap =
                    PandaPathfinding.getPandaMoves(board, botState);

            assertThat(completionMap.keySet()).hasSize(1);
            assertThat(
                            completionMap.get(
                                    new PandaObjective(
                                            Map.of(
                                                    TileColor.GREEN,
                                                    1,
                                                    TileColor.YELLOW,
                                                    1,
                                                    TileColor.PINK,
                                                    1),
                                            100)))
                    .hasSize(3)
                    .containsExactlyInAnyOrder(
                            new PositionVector(0, 1, -1),
                            new PositionVector(0, 2, -2),
                            new PositionVector(0, 3, -3));
        }
    }
}

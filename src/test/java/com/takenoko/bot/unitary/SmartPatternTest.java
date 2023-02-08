package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.*;
import com.takenoko.objective.PatternObjective;
import com.takenoko.shape.Curve;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SmartPatternTest {

    @Nested
    @DisplayName("fillAction")
    class fillAction {
        @Test
        @DisplayName("When given a pattern objective, should try to complete it")
        void shouldTryToCompletePatternObjective() {
            Map<PositionVector, Tile> m = new HashMap<>();
            m.put(new PositionVector(0, 0, 0), new Pond());
            m.put(new PositionVector(1, 0, -1), new Tile(TileColor.PINK));
            m.put(new PositionVector(1, -1, 0), new Tile(TileColor.PINK));
            m.put(new PositionVector(0, -1, 1), new Tile(TileColor.GREEN));

            Board board = mock(Board.class);
            for (Map.Entry<PositionVector, Tile> entry : m.entrySet()) {
                when(board.getTileAt(entry.getKey())).thenReturn(entry.getValue());
                when(board.isTile(entry.getKey())).thenReturn(true);
                when(board.isIrrigatedAt(entry.getKey())).thenReturn(true);
            }
            when(board.getTilesWithoutPond())
                    .thenReturn(
                            m.entrySet().stream()
                                    .filter(k -> k.getValue().getType() != TileType.POND)
                                    .collect(
                                            Collectors.toMap(
                                                    Map.Entry::getKey, Map.Entry::getValue)));
            when(board.getTiles())
                    .thenReturn(
                            m.entrySet().stream()
                                    .collect(
                                            Collectors.toMap(
                                                    Map.Entry::getKey, Map.Entry::getValue)));
            when(board.peekTileDeck())
                    .thenReturn(
                            List.of(
                                    new Tile(TileColor.PINK),
                                    new Tile(TileColor.PINK),
                                    new Tile(TileColor.PINK)));
            when(board.getAvailableTilePositions())
                    .thenReturn(
                            List.of(
                                    new PositionVector(1, -1, 0),
                                    new PositionVector(1, 0, -1),
                                    new PositionVector(0, -1, 1),
                                    new PositionVector(-1, 0, 1),
                                    new PositionVector(-1, 1, 0)));

            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions()).thenReturn(List.of(PlaceTileAction.class));
            when(botState.getNotAchievedObjectives())
                    .thenReturn(List.of(new PatternObjective(new Curve(TileColor.PINK), 100)));
            when(botState.getInventory()).thenReturn(mock(Inventory.class));
            when(botState.getInventory().hasImprovement()).thenReturn(false);
            when(botState.getInventory().hasImprovement(ImprovementType.WATERSHED))
                    .thenReturn(false);

            SmartPattern smartPattern = new SmartPattern();
            smartPattern.fillAction(board, botState, mock(History.class));

            assertThat(smartPattern.chooseAction(board, botState, mock(History.class)))
                    .isInstanceOf(PlaceTileAction.class);
        }
    }
}

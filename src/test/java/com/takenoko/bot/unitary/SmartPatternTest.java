package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.layers.tile.TileType;
import com.takenoko.objective.PatternObjective;
import com.takenoko.shape.Curve;
import com.takenoko.shape.Pattern;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SmartPatternTest {

    @Nested
    @DisplayName("fillAction")
    class fillAction {
        @Test
        @DisplayName("Should call FullrandomBot when there is no PlaceIrrigationFromInventoryAction")
        void shouldCallFullrandomBotWhenThereIsNoPlaceIrrigationFromInventoryAction() {
            // TODO
        }

        @Test
        @DisplayName("When given a pattern objective, should try to complete it")
        void shouldTryToCompletePatternObjective() {
            Map<Tile, PositionVector> m = new HashMap<Tile, PositionVector>();
            m.put(new Pond(), new PositionVector(0, 0, 0));
            m.put(new Tile(TileColor.PINK), new PositionVector(1, 0, -1));
            m.put(new Tile(TileColor.PINK), new PositionVector(1, -1, 0));
            m.put(new Tile(TileColor.GREEN), new PositionVector(0, -1, 1));

            Board board = mock(Board.class);
            for (Map.Entry<Tile, PositionVector> entry : m.entrySet()) {
                when(board.getTileAt(entry.getValue())).thenReturn(entry.getKey());
                when(board.isTile(entry.getValue())).thenReturn(true);
                when(board.isIrrigatedAt(entry.getValue())).thenReturn(true);
            }
            when(board.getTilesWithoutPond()).thenReturn(m.entrySet().stream().filter(k -> k.getKey().getType() != TileType.POND).collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey)));
            when(board.getTiles()).thenReturn(m.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey)));
            when(board.peekTileDeck()).thenReturn(List.of(new Tile(TileColor.PINK), new Tile(TileColor.PINK),new Tile(TileColor.PINK)));

            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions()).thenReturn(List.of(PlaceTileAction.class));
            when(botState.getNotAchievedObjectives()).thenReturn(List.of(new PatternObjective(new Curve(TileColor.PINK), 100)));

            SmartPattern smartPattern = new SmartPattern();
            smartPattern.fillAction(board, botState, mock(History.class));

            assertThat(smartPattern.chooseAction(board, botState, mock(History.class))).isInstanceOf(PlaceTileAction.class);
        }
    }

}
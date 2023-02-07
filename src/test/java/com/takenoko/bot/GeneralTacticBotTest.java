package com.takenoko.bot;

import static org.mockito.Mockito.*;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.asset.TileDeck;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.irrigation.IrrigationLayer;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GeneralTacticBotTest {
    @Nested
    @DisplayName("method analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective")
    class TestAnalyzeBoardToFindPlaceToCompleteShapeOfPatternObjective {
        @Test
        @DisplayName("should return the only place to complete the shape of the pattern objective")
        void shouldReturnTheOnlyPlaceToCompleteTheShapeOfThePatternObjective() {
            GeneralTacticBot generalTacticBot = new GeneralTacticBot();

            TileDeck tileDeck = mock(TileDeck.class);
            GameAssets gameAssets = mock(GameAssets.class);
            when(gameAssets.getTileDeck()).thenReturn(tileDeck);
            when(tileDeck.peek())
                    .thenReturn(List.of(new Tile(TileColor.GREEN)))
                    .thenReturn(List.of(new Tile(TileColor.YELLOW)))
                    .thenReturn(List.of(new Tile(TileColor.GREEN)))
                    .thenReturn(List.of(new Tile(TileColor.YELLOW)))
                    .thenReturn(List.of(new Tile(TileColor.GREEN)))
                    .thenReturn(List.of(new Tile(TileColor.YELLOW)));
            when(gameAssets.copy()).thenReturn(gameAssets);

            Board board =
                    new Board(
                            new TileLayer(),
                            new BambooLayer(),
                            new Panda(),
                            new Gardener(),
                            gameAssets,
                            new IrrigationLayer());
            board = spy(board);

            board.drawTiles();
            Tile tileToPlace = board.peekTileDeck().get(0);
            board.placeTile(tileToPlace, new PositionVector(0, -1, 1));
            board.drawTiles();
            tileToPlace = board.peekTileDeck().get(0);
            board.placeTile(tileToPlace, new PositionVector(1, -1, 0));
            board.drawTiles();
            tileToPlace = board.peekTileDeck().get(0);
            board.placeTile(tileToPlace, new PositionVector(1, -2, 1));
            board.drawTiles();
            tileToPlace = board.peekTileDeck().get(0);
            board.placeTile(tileToPlace, new PositionVector(2, -2, 0));
            board.drawTiles();
            tileToPlace = board.peekTileDeck().get(0);
            board.placeTile(tileToPlace, new PositionVector(2, -3, 1));

            //
            BotState botState = mock(BotState.class);

            // When
            // TODO: move this test to SmartPattern
            /**
             * when(generalTacticBot.getCurrentPatternObjectives(botState)) .thenReturn(List.of(new
             * PatternObjective(new Line(TileColor.YELLOW), 2)));
             *
             * <p>// Execute method Pair<PositionVector, Tile> result =
             * generalTacticBot.analyzeBoardToFindPlaceToCompleteShapeOfPatternObjective( board,
             * botState); assertThat(result) .isEqualTo(Pair.of(new PositionVector(3, -3, 0), new
             * Tile(TileColor.YELLOW)));
             */
        }
    }
}

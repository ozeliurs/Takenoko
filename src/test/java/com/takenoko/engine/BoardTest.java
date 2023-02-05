package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.irrigation.IrrigationLayer;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {
    Board board;
    TileLayer tileLayer;
    BambooLayer bambooLayer;
    Panda panda;
    Gardener gardener;
    GameAssets gameAssets;

    @BeforeEach
    void setUp() {
        tileLayer = mock(TileLayer.class);
        bambooLayer = mock(BambooLayer.class);
        panda = mock(Panda.class);
        gardener = mock(Gardener.class);
        gameAssets = mock(GameAssets.class);

        board =
                new Board(
                        tileLayer, bambooLayer, panda, gardener, gameAssets, new IrrigationLayer());
    }

    @Test
    @DisplayName("Method getAvailableTiles")
    void shouldReturnAListOfAvailableTiles() {
        List<Tile> availableTiles = List.of(new Tile(), new Tile());
        when(tileLayer.getAvailableTiles()).thenReturn(availableTiles);

        board.getAvailableTiles();
        verify(tileLayer, times(1)).getAvailableTiles();
        assertThat(board.getAvailableTiles()).isEqualTo(availableTiles);
    }

    @Test
    @DisplayName("Method getTiles")
    void shouldReturnAListOfTiles() {
        HashMap<PositionVector, Tile> tiles = new HashMap<>();
        tiles.put(new PositionVector(0, 0, 0), new Tile());
        when(tileLayer.getTiles()).thenReturn(tiles);

        board.getTiles();
        verify(tileLayer, times(1)).getTiles();
        assertThat(board.getTiles()).isEqualTo(tiles);
    }

    @Test
    @DisplayName("Method getTilesWithoutPond")
    void shouldReturnAListOfTilesWithoutPond() {
        HashMap<PositionVector, Tile> tiles = new HashMap<>();
        tiles.put(new PositionVector(0, 0, 0), new Tile());
        when(tileLayer.getTilesWithoutPond()).thenReturn(tiles);

        board.getTilesWithoutPond();
        verify(tileLayer, times(1)).getTilesWithoutPond();
        assertThat(board.getTilesWithoutPond()).isEqualTo(tiles);
    }

    @Test
    @DisplayName("Method isTile")
    void shouldReturnTrueIfTileIsPresent() {
        PositionVector positionVector = new PositionVector(0, 0, 0);

        board.isTile(positionVector);
        verify(tileLayer, times(1)).isTile(positionVector);

        when(tileLayer.isTile(positionVector)).thenReturn(true);
        assertThat(board.isTile(positionVector)).isTrue();

        when(tileLayer.isTile(positionVector)).thenReturn(false);
        assertThat(board.isTile(positionVector)).isFalse();
    }

    @Test
    @DisplayName("Method getTileAt")
    void shouldReturnATileAtAGivenPosition() {
        PositionVector position = new PositionVector(0, 0, 0);
        when(tileLayer.getTileAt(position)).thenReturn(new Tile());

        board.getTileAt(position);
        verify(tileLayer, times(1)).getTileAt(position);
        assertThat(board.getTileAt(position)).isEqualTo(new Tile());
    }

    @Test
    @DisplayName("Method placeTile")
    void shouldPlaceATileAtAGivenPosition() {
        PositionVector position = new PositionVector(1, 0, -1);
        Tile tile = new Tile();
        when(tileLayer.placeTile(tile, position, board)).thenReturn(new LayerBambooStack(1));

        board.placeTile(tile, position);
        verify(tileLayer, times(1)).placeTile(tile, position, board);
        assertThat(board.placeTile(tile, position)).isEqualTo(new LayerBambooStack(1));
    }

    @Test
    @DisplayName("Method getBambooAt")
    void shouldReturnABambooAtAGivenPosition() {
        PositionVector position = new PositionVector(0, 0, 0);
        when(bambooLayer.getBambooAt(position, board)).thenReturn(new LayerBambooStack(1));

        board.getBambooAt(position);
        verify(bambooLayer, times(1)).getBambooAt(position, board);
        assertThat(board.getBambooAt(position)).isEqualTo(new LayerBambooStack(1));
    }

    @Test
    @DisplayName("Method growBamboo")
    void shouldGrowBambooAtAGivenPosition() {
        PositionVector position = new PositionVector(1, 0, -1);

        board.growBamboo(position);
        verify(bambooLayer, times(1)).growBamboo(position, board);
    }

    @Test
    @DisplayName("Method eatBamboo")
    void shouldEatBambooAtAGivenPosition() {
        PositionVector position = new PositionVector(1, 0, -1);

        board.eatBamboo(position);
        verify(bambooLayer, times(1)).eatBamboo(position, board);
    }

    @Test
    @DisplayName("Method getPandaPosition")
    void shouldReturnPandaPosition() {
        PositionVector position = new PositionVector(1, 0, -1);
        when(panda.getPosition()).thenReturn(position);

        board.getPandaPosition();
        verify(panda, times(1)).getPosition();
        assertThat(board.getPandaPosition()).isEqualTo(position);
    }

    @Test
    @DisplayName("Method getGardenerPosition")
    void shouldReturnGardenerPosition() {
        PositionVector position = new PositionVector(1, 0, -1);
        when(gardener.getPosition()).thenReturn(position);

        board.getGardenerPosition();
        verify(gardener, times(1)).getPosition();
        assertThat(board.getGardenerPosition()).isEqualTo(position);
    }

    @Test
    @DisplayName("Method getPandaPossibleMoves")
    void shouldReturnPandaPossibleMoves() {
        List<PositionVector> possibleMoves =
                List.of(new PositionVector(1, 0, -1), new PositionVector(0, 1, -1));
        when(panda.getPossibleMoves(board)).thenReturn(possibleMoves);

        board.getPandaPossibleMoves();
        verify(panda, times(1)).getPossibleMoves(board);
        assertThat(board.getPandaPossibleMoves()).isEqualTo(possibleMoves);
    }

    @Test
    @DisplayName("Method getGardenerPossibleMoves")
    void shouldReturnGardenerPossibleMoves() {
        List<PositionVector> possibleMoves =
                List.of(new PositionVector(1, 0, -1), new PositionVector(0, 1, -1));
        when(gardener.getPossibleMoves(board)).thenReturn(possibleMoves);

        board.getGardenerPossibleMoves();
        verify(gardener, times(1)).getPossibleMoves(board);
        assertThat(board.getGardenerPossibleMoves()).isEqualTo(possibleMoves);
    }

    @Test
    @DisplayName("Method movePanda")
    void shouldMovePandaToAGivenPosition() {
        PositionVector position = new PositionVector(0, 0, 0);
        when(panda.move(position, board)).thenReturn(Map.of(position, new LayerBambooStack(1)));

        board.movePanda(position);
        verify(panda, times(1)).move(position, board);
        assertThat(board.movePanda(position)).containsEntry(position, new LayerBambooStack(1));
    }

    @Test
    @DisplayName("Method moveGardener")
    void shouldMoveGardenerToAGivenPosition() {
        PositionVector position = new PositionVector(0, 0, 0);
        when(panda.move(position, board)).thenReturn(Map.of(position, new LayerBambooStack(1)));

        board.moveGardener(position);
        verify(gardener, times(1)).move(position, board);
        assertThat(board.movePanda(position)).containsEntry(position, new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When called on itself should return true")
        @SuppressWarnings("EqualsWithItself")
        void EqualsWithItselfIsTrue() {
            assertThat(board.equals(board)).isTrue();
        }

        @Test
        @DisplayName("When boards are equal, returns true")
        void equals_WhenBoardsAreEqual_ThenReturnsTrue() {
            Board board = new Board();
            Board board2 = new Board();
            assertThat(board).isEqualTo(board2);
        }

        @Test
        @DisplayName("When boards are not equal, returns false")
        void equals_WhenBoardsAreNotEqual_ThenReturnsFalse() {
            Board board = new Board();
            Board board2 =
                    new Board(
                            mock(TileLayer.class),
                            mock(BambooLayer.class),
                            mock(Panda.class),
                            mock(Gardener.class),
                            mock(GameAssets.class),
                            new IrrigationLayer());
            assertThat(board).isNotEqualTo(board2);
        }

        @Test
        @DisplayName("When boards is null, returns false")
        void equals_WhenBoardIsNull_ThenReturnsFalse() {
            Board board = new Board();
            assertThat(board).isNotEqualTo(null);
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When boards are equal, returns same hash code")
        void hashCode_WhenBoardsAreEqual_ThenReturnsSameHashCode() {
            Board board = new Board();
            Board board2 = new Board();
            assertThat(board).hasSameHashCodeAs(board2);
        }

        @Test
        @DisplayName("When boards are not equal, returns different hash code")
        void hashCode_WhenBoardsAreNotEqual_ThenReturnsDifferentHashCode() {
            Board board = new Board();
            Board board2 =
                    new Board(
                            mock(TileLayer.class),
                            mock(BambooLayer.class),
                            mock(Panda.class),
                            mock(Gardener.class),
                            mock(GameAssets.class),
                            new IrrigationLayer());
            assertThat(board).doesNotHaveSameHashCodeAs(board2);
        }
    }

    @Nested
    @DisplayName("Method copy")
    class TestCopy {
        @Test
        @DisplayName("When board is copied, returns a new board")
        void copy_WhenBoardIsCopied_ThenReturnsNewBoard() {
            Board board = new Board();
            assertThat(board.copy()).isNotSameAs(board).isEqualTo(board);
        }
    }

    @Nested
    @DisplayName("Method applyImprovement")
    class TestApplyImprovement {
        @Test
        @DisplayName("When improvement is applied calls applyImprovement on" + " tile layer")
        void applyImprovement_WhenImprovementIsApplied_CallsApplyImprovementOnTileLayer() {
            board.applyImprovement(mock(ImprovementType.class), mock(PositionVector.class));
            verify(tileLayer).applyImprovement(any(), any(), any());
        }
    }
}

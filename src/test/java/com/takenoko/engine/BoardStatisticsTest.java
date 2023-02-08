package com.takenoko.engine;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.irrigation.IrrigationLayer;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardStatisticsTest {
    private BoardStatistics boardStatistics;
    private Board board;

    @BeforeEach
    void setUp(){
        boardStatistics=new BoardStatistics();
        board=mock(Board.class);
    }

    @AfterEach
    void tearDown(){
        boardStatistics=null;
    }

    @Nested
    @DisplayName("Test Update Improvements")
    class TestUpdateObjectivesRedeemed {
        @Test
        @DisplayName("when the improvement is of new type, should add new entry")
        void whenTheUpdatedImprovementIsNew_shouldAddNewEntry() {
            ImprovementType improvementType = mock(ImprovementType.class);
            int oldSize = boardStatistics.getImprovements().size();
            boardStatistics.updateImprovements(improvementType);
            assertThat(boardStatistics.getImprovements()).hasSize(oldSize + 1);
        }

        @Test
        @DisplayName("when the improvement isn't of new type, shouldn't add new entry")
        void whenTheUpdatedImprovementIsntNew_shouldntAddNewEntry() {
            ImprovementType improvementType = mock(ImprovementType.class);
            boardStatistics.updateImprovements(improvementType);
            int oldSize = boardStatistics.getImprovements().size();
            boardStatistics.updateImprovements(improvementType);
            assertThat(boardStatistics.getImprovements()).hasSize(oldSize);
        }

        @Test
        @DisplayName("should increment number of improvements for the right type")
        void shouldIncrementNumberOfImprovementForTheRightType() {
            ImprovementType improvementType = mock(ImprovementType.class);
            boardStatistics.updateImprovements(improvementType);
            int oldValue = boardStatistics.getImprovements().get(improvementType);
            boardStatistics.updateImprovements(improvementType);
            assertThat(boardStatistics.getImprovements())
                    .containsEntry(improvementType, oldValue + 1);
        }

        @Test
        @DisplayName("if parameter is null, should throw exception")
        void ifParameterIsNull_shouldThrowException() {
            assertThatThrownBy(() -> boardStatistics.updateImprovements(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Test analyzeBoard")
    class TestAnalyzeBoard {
        @Test
        @DisplayName("General tests")
        void GeneralTests() {
            boardStatistics=spy(BoardStatistics.class);
            TileLayer tileLayer=mock(TileLayer.class);
            IrrigationLayer irrigationLayer=mock(IrrigationLayer.class);
            Optional<ImprovementType> improvementType=Optional.of(ImprovementType.FERTILIZER);
            board=new Board(tileLayer,
                    mock(BambooLayer.class),
                    mock(Panda.class),
                    mock(Gardener.class),
                    mock(GameAssets.class),
                    irrigationLayer,
                    boardStatistics);
            HashMap<PositionVector,Tile> map=new HashMap<>();
            PositionVector positionVector1=mock(PositionVector.class);
            Tile tile1=mock(Tile.class);
            PositionVector positionVector2=mock(PositionVector.class);
            Tile tile2=mock(Tile.class);
            map.put(positionVector1,tile1);
            map.put(positionVector2,tile2);
            when(tileLayer.getTilesWithoutPond()).thenReturn(map);
            when(tile1.getColor()).thenReturn(TileColor.GREEN);
            when(tile2.getColor()).thenReturn(TileColor.PINK);
            when(tile1.getImprovement()).thenReturn(improvementType);
            when(tile2.getImprovement()).thenReturn(Optional.ofNullable(null));
            when(irrigationLayer.isIrrigatedAt(positionVector1)).thenReturn(true);
            when(irrigationLayer.isIrrigatedAt(positionVector2)).thenReturn(false);
            boardStatistics.analyzeBoard(board);
            verify(boardStatistics, times(1)).updateImprovements(any());
            verify(boardStatistics, times(2)).updateTilesPlaced(any());
            assertThat(boardStatistics.getTotalNbOfTiles()).isEqualTo(Float.valueOf(2));
            assertThat(boardStatistics.getPercentageOfIrrigation()).isEqualTo(Float.valueOf(50));
        }
        @Test
        @DisplayName("should throw exception if argument is null")
        void shouldThrowExceptionIfArgumentIsNull() {
            assertThatThrownBy(() -> boardStatistics.analyzeBoard(null)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}

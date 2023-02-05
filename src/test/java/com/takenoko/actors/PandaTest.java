package com.takenoko.actors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.WeatherFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PandaTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        HashMap<PositionVector, Tile> tiles = new HashMap<>();
        tiles.put(new PositionVector(0, 0, 0), new Pond());
        tiles.put(new PositionVector(1, 0, -1), new Tile());
        tiles.put(new PositionVector(1, -1, 0), new Tile());
        tiles.put(new PositionVector(2, -1, -1), new Tile());
        tiles.put(new PositionVector(2, -2, 0), new Tile());
        tiles.put(new PositionVector(1, -2, 1), new Tile());
        tiles.put(new PositionVector(2, -3, 1), new Tile());
        tiles.put(new PositionVector(1, -3, 2), new Tile());
        tiles.put(new PositionVector(0, -2, 2), new Tile());
        when(board.getTiles()).thenReturn(tiles);
        when(board.placeTile(any(), any())).thenReturn(new LayerBambooStack(1));
        when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(1)); // Probably not right
        for (PositionVector position : tiles.keySet()) {
            when(board.isTile(position)).thenReturn(true);
        }
    }

    @Nested
    @DisplayName("Method getPosition()")
    class TestGetPosition {
        @Test
        @DisplayName("should return the position of the panda")
        void shouldReturnThePositionOfThePanda() {
            Panda panda = new Panda();
            assertEquals(new PositionVector(0, 0, 0), panda.getPosition());
        }
    }

    @Nested
    @DisplayName("Method positionMessage()")
    class TestPositionMessage {
        @Test
        @DisplayName("should return a string explaining where the panda is on the board")
        void shouldReturnAStringExplainingWhereThePandaIsOnTheBoard() {
            Panda panda = new Panda();
            assertEquals("The panda is at Vector[q=0.0, r=0.0, s=0.0]", panda.positionMessage());
        }
    }

    @Nested
    @DisplayName("Method move()")
    class TestMove {

        @Test
        @DisplayName("should move the panda with a valid vector")
        void shouldMoveThePandaWithAVector() {
            Panda panda = new Panda();
            LayerBambooStack layerBambooStack = mock(LayerBambooStack.class);
            when(layerBambooStack.isEmpty()).thenReturn(false);
            when(board.getBambooAt(any())).thenReturn(layerBambooStack);

            panda.move(new PositionVector(1, 0, -1), board);
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, 0, -1));

            panda.move(new PositionVector(0, -1, 1), board);
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, -1, 0));

            panda.move(new PositionVector(1, -1, -0), board);
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(2, -2, 0));
        }

        @Test
        @DisplayName("should throw an exception if the panda is not moving with a valid vector")
        void shouldThrowAnExceptionIfThePandaIsNotMovingWithAValidVector() {
            Panda panda = new Panda();
            PositionVector vector = new PositionVector(1, 1, -2);
            assertThatThrownBy(() -> panda.move(vector, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("This move is not possible");
        }
    }

    @Nested
    @DisplayName("Method afterMove()")
    class TestAfterMove {
        @Test
        @DisplayName("should eat a bamboo if there is one")
        void shouldEatABambooIfThereIsOne() {
            Panda panda = new Panda();
            when(board.isBambooEatableAt(any())).thenReturn(true);
            Map<PositionVector, LayerBambooStack> stack =
                    panda.move(new PositionVector(1, 0, -1), board);
            verify(board, times(1)).eatBamboo(any());
            assertThat(stack).hasSize(1);
        }

        @Test
        @DisplayName("should not eat a bamboo if there is none")
        void shouldNotEatABambooIfThereIsNone() {
            Panda panda = new Panda();

            when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(0));

            Map<PositionVector, LayerBambooStack> stack =
                    panda.move(new PositionVector(1, 0, -1), board);
            assertThat(stack).isEmpty();
        }
    }

    @Nested
    @DisplayName("Method getPossibleMoves()")
    class TestGetPossibleMoves {
        @Test
        @DisplayName("should return a list of possible moves")
        void shouldReturnAListOfPossibleMoves() {
            Panda panda = new Panda();
            assertThat(panda.getPossibleMoves(board))
                    .containsExactlyInAnyOrder(
                            new PositionVector(1, -1, 0),
                            new PositionVector(2, -2, 0),
                            new PositionVector(1, 0, -1));
        }

        @Test
        @DisplayName("should return all position if weather is storm")
        void shouldReturnAllPositionIfWeatherIsStorm() {
            Panda panda = new Panda();
            when(board.getWeather()).thenReturn(Optional.of(WeatherFactory.STORMY.createWeather()));
            assertThat(panda.getPossibleMoves(board))
                    .containsExactlyInAnyOrder(
                            new PositionVector(1, 0, -1),
                            new PositionVector(1, -1, 0),
                            new PositionVector(2, -1, -1),
                            new PositionVector(2, -2, 0),
                            new PositionVector(1, -2, 1),
                            new PositionVector(2, -3, 1),
                            new PositionVector(1, -3, 2),
                            new PositionVector(0, -2, 2));
        }
    }

    @Nested
    @DisplayName("Method equals()")
    class TestEquals {
        @Test
        @DisplayName("should return true if the two objects are equal")
        void shouldReturnTrueIfTheTwoObjectsAreEqual() {
            Panda panda = new Panda();
            Panda panda2 = new Panda();
            assertThat(panda).isEqualTo(panda2);
        }

        @Test
        @DisplayName("should return true if the two objects are the same")
        void equals_shouldReturnTrueIfTheTwoObjectsAreTheSame() {
            Panda panda = new Panda();
            assertEquals(panda, panda);
        }

        @Test
        @DisplayName("should return false if the two objects are of different classes")
        void shouldReturnFalseIfTheTwoObjectsAreOfDifferentClasses() {
            Panda panda = new Panda();
            Object object = new Object();
            assertThat(panda).isNotEqualTo(object);
        }

        @Test
        @DisplayName("should return false if the two objects are not the same")
        void shouldReturnFalseIfTheTwoObjectsAreNotTheSame() {
            Panda panda = new Panda();
            Panda panda2 = new Panda();
            LayerBambooStack layerBambooStack = mock(LayerBambooStack.class);
            when(layerBambooStack.isEmpty()).thenReturn(false);
            when(board.getBambooAt(any())).thenReturn(layerBambooStack);
            panda2.move(new PositionVector(1, 0, -1), board);
            assertThat(panda).isNotEqualTo(panda2);
        }
    }

    @Nested
    @DisplayName("Method hashCode()")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hashcode if the two objects are equal")
        void shouldReturnTheSameHashcodeIfTheTwoObjectsAreEqual() {
            Panda panda = new Panda();
            Panda panda2 = new Panda();
            assertThat(panda).hasSameHashCodeAs(panda2);
        }

        @Test
        @DisplayName("should return a different hashcode if the two objects are not the same")
        void shouldReturnADifferentHashcodeIfTheTwoObjectsAreNotTheSame() {
            Panda panda = new Panda();
            Panda panda2 = new Panda();
            LayerBambooStack layerBambooStack = mock(LayerBambooStack.class);
            when(layerBambooStack.isEmpty()).thenReturn(false);
            when(board.getBambooAt(any())).thenReturn(layerBambooStack);
            panda2.move(new PositionVector(1, 0, -1), board);
            assertThat(panda).doesNotHaveSameHashCodeAs(panda2);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the panda")
        void shouldReturnACopyOfThePanda() {
            Panda panda = new Panda();
            Panda panda2 = panda.copy();
            assertThat(panda).isEqualTo(panda2).isNotSameAs(panda2);
        }
    }
}

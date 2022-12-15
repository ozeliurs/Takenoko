package com.takenoko.actors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.takenoko.layers.Board;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PandaTest {

    @Nested
    @DisplayName("Method getPosition()")
    class TestGetPosition {
        @Test
        @DisplayName("should return the position of the panda")
        void shouldReturnThePositionOfThePanda() {
            Panda panda = new Panda(new Board());
            assertEquals(new PositionVector(0, 0, 0), panda.getPosition());
        }
    }

    @Nested
    @DisplayName("Method positionMessage()")
    class TestPositionMessage {
        @Test
        @DisplayName("should return a string explaining where the panda is on the board")
        void shouldReturnAStringExplainingWhereThePandaIsOnTheBoard() {
            Panda panda = new Panda(new Board());
            assertEquals("The panda is at Vector[q=0.0, r=0.0, s=0.0]", panda.positionMessage());
        }
    }

    @Nested
    @DisplayName("Method move()")
    class TestMove {

        private Board board;

        @BeforeEach
        void setUp() {
            board = new Board();
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, 0, -1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, -1, 0));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(2, -1, -1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(2, -2, 0));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, -2, 1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(2, -3, 1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, -3, 2));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(0, -2, 2));
        }

        @Test
        @DisplayName("should move the panda with a valid vector")
        void shouldMoveThePandaWithAVector() {
            Panda panda = new Panda(board);
            panda.move(new PositionVector(1, 0, -1));
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, 0, -1));

            panda.move(new PositionVector(0, -1, 1));
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, -1, 0));

            panda.move(new PositionVector(1, -1, -0));
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(2, -2, 0));
        }

        @Test
        @DisplayName("should throw an exception if the panda is not moving with a valid vector")
        void shouldThrowAnExceptionIfThePandaIsNotMovingWithAValidVector() {
            Panda panda = new Panda(board);
            Vector vector = new PositionVector(1, 1, -2);
            assertThatThrownBy(() -> panda.move(vector))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("This move is not possible");
        }
    }

    @Nested
    @DisplayName("Method getPossibleMoves()")
    class TestGetPossibleMoves {
        private Board board;

        @BeforeEach
        void setUp() {
            board = new Board();
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, 0, -1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, -1, 0));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(2, -1, -1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(2, -2, 0));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, -2, 1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(2, -3, 1));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(1, -3, 2));
            board.getTileLayer().placeTile(new Tile(), new PositionVector(0, -2, 2));
        }

        @Test
        @DisplayName("should return a list of possible moves")
        void shouldReturnAListOfPossibleMoves() {
            Panda panda = new Panda(board);
            assertThat(panda.getPossibleMoves())
                    .containsExactlyInAnyOrder(
                            new PositionVector(1, -1, 0),
                            new PositionVector(2, -2, 0),
                            new PositionVector(1, 0, -1));
        }
    }
}

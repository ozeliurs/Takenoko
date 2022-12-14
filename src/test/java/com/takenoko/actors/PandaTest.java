package com.takenoko.actors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.takenoko.Board;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
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

        private Board board;

        @BeforeEach
        void setUp() {
            board = new Board();
            board.placeTile(new Tile(), new PositionVector(1, 0, -1));
            board.placeTile(new Tile(), new PositionVector(1, -1, 0));
            board.placeTile(new Tile(), new PositionVector(2, -1, -1));
            board.placeTile(new Tile(), new PositionVector(2, -2, 0));
            board.placeTile(new Tile(), new PositionVector(1, -2, 1));
            board.placeTile(new Tile(), new PositionVector(2, -3, 1));
            board.placeTile(new Tile(), new PositionVector(1, -3, 2));
            board.placeTile(new Tile(), new PositionVector(0, -2, 2));
        }

        @Test
        @DisplayName("should move the panda with a vector")
        void shouldMoveThePandaWithAVector() {
            Panda panda = new Panda();
            panda.move(new PositionVector(1, 0, -1), board);
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, 0, -1));
        }

        @Test
        @DisplayName(
                "should throw an IllegalArgumentException if the panda is not moving in a straight"
                        + " line")
        void shouldThrowAnIllegalArgumentExceptionIfThePandaIsNotMovingInAStraightLine() {
            Panda panda = new Panda();
            PositionVector vector = new PositionVector(1, 1, -2);
            assertThatThrownBy(() -> panda.move(vector, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The panda must move in a straight line");
        }

        @Test
        @DisplayName(
                "should throw an IllegalArgumentException if the panda is not moving on a tile")
        void shouldThrowAnIllegalArgumentExceptionIfThePandaIsNotMovingOnATile() {
            Panda panda = new Panda();
            PositionVector vector = new PositionVector(0, -2, 2);
            assertThatThrownBy(() -> panda.move(vector, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The panda must move on a tile");
        }
    }
}

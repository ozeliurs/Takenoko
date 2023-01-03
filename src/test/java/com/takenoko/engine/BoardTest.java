package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.takenoko.actors.panda.Panda;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.tile.TileLayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Nested
    @DisplayName("Method equals")
    class TestEquals {
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
                    new Board(mock(TileLayer.class), mock(BambooLayer.class), mock(Panda.class));
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
                    new Board(mock(TileLayer.class), mock(BambooLayer.class), mock(Panda.class));
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
}

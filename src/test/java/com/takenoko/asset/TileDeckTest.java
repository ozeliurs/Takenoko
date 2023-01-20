package com.takenoko.asset;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TileDeckTest {

    @Nested
    @DisplayName("constructor")
    class Constructor {
        @Test
        @DisplayName("When creating a tile deck, the deck is not empty and contains 23 tiles")
        void whenCreatingATileDeckTheDeckIsNotEmptyAndContains23Tiles() {
            TileDeck tileDeck = new TileDeck();
            assertFalse(tileDeck.isEmpty());
            assertThat(tileDeck).hasSize(23);
        }
    }

    @Nested
    @DisplayName("draw && peek")
    class DrawAndPeek {
        @Test
        @DisplayName("When drawinf tiles three tiles are drawn")
        void whenDrawinfTilesThreeTilesAreDrawn() {
            TileDeck tileDeck = new TileDeck();
            tileDeck.draw();
            assertThat(tileDeck.peek()).hasSize(3);
        }

        @Test
        @DisplayName("When drawing tiles, the last drawn tiles are cleared")
        void whenDrawingTilesTheLastDrawnTilesAreCleared() {
            TileDeck tileDeck = new TileDeck();
            tileDeck.draw();
            tileDeck.draw();
            assertThat(tileDeck.peek()).hasSize(3);
        }
    }

    @Nested
    @DisplayName("choose")
    class Choose {
        @Test
        @DisplayName(
                "When choosing a tile that is not present in the last drawn tiles, an exception is"
                        + " thrown")
        void whenChoosingATileThatIsNotPresentInTheLastDrawnTilesAnExceptionIsThrown() {
            TileDeck tileDeck = new TileDeck();
            tileDeck.draw();
            Tile tile = new Pond();
            assertThatThrownBy(() -> tileDeck.choose(tile))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("equals")
    class Equals {
        @Test
        @DisplayName("When comparing two tile decks, they are equal if they have the same tiles")
        void whenComparingTwoTileDecksTheyAreEqualIfTheyHaveTheSameTiles() {
            TileDeck tileDeck1 = new TileDeck();
            TileDeck tileDeck2 = new TileDeck();
            assertThat(tileDeck1).isEqualTo(tileDeck2);
        }

        @Test
        @DisplayName(
                "When comparing two tile decks, they are not equal if they have different tiles")
        void whenComparingTwoTileDecksTheyAreNotEqualIfTheyHaveDifferentTiles() {
            TileDeck tileDeck1 = new TileDeck();
            TileDeck tileDeck2 = new TileDeck();
            tileDeck2.draw();
            tileDeck2.choose(tileDeck2.peek().get(0));
            assertThat(tileDeck1).isNotEqualTo(tileDeck2);
        }

        @Test
        @DisplayName("Return false when comparing a tile deck to Object")
        void returnFalseWhenComparingATileDeckToObject() {
            TileDeck tileDeck = new TileDeck();
            Object object = new Object();
            assertThat(tileDeck).isNotEqualTo(object);
        }

        @Test
        @DisplayName("Return true when comparing to self")
        void equals_returnTrueWhenComparingToSelf() {
            TileDeck tileDeck = new TileDeck();
            assertThat(tileDeck).isEqualTo(tileDeck);
        }
    }

    @Nested
    @DisplayName("hashCode")
    class HashCode {
        @Test
        @DisplayName(
                "When comparing two tile decks, they have the same hash code if they have the same"
                        + " tiles")
        void whenComparingTwoTileDecksTheyHaveTheSameHashCodeIfTheyHaveTheSameTiles() {
            TileDeck tileDeck1 = new TileDeck();
            TileDeck tileDeck2 = new TileDeck();
            assertThat(tileDeck1).hasSameHashCodeAs(tileDeck2);
        }

        @Test
        @DisplayName(
                "When comparing two tile decks, they have different hash codes if they have"
                        + " different tiles")
        void whenComparingTwoTileDecksTheyHaveDifferentHashCodesIfTheyHaveDifferentTiles() {
            TileDeck tileDeck1 = new TileDeck();
            TileDeck tileDeck2 = new TileDeck();
            tileDeck2.draw();
            tileDeck2.choose(tileDeck2.peek().get(0));
            assertThat(tileDeck1).doesNotHaveSameHashCodeAs(tileDeck2);
        }
    }
}

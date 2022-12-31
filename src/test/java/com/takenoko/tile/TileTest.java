package com.takenoko.tile;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileType;
import org.junit.jupiter.api.*;

class TileTest {
    private Tile tile;
    private Tile pondTile;

    @BeforeEach
    void setUp() {
        pondTile = new Tile(TileType.POND);
        tile = new Tile();
    }

    @AfterEach
    void tearDown() {
        pondTile = null;
        tile = null;
    }

    @Nested
    @DisplayName("Method getType")
    class TestGetType {
        @Test
        @DisplayName("When tile is created, it is not a pond.")
        void getType_WhenCalled_ThenReturnsFalse() {
            assertThat(tile.getType()).isNotEqualTo(TileType.POND);
        }

        @Test
        @DisplayName("When tiles is Pond, returns TileType POND")
        void getType_WhenTileIsPond_ThenReturnsTrue() {
            assertThat(pondTile.getType()).isEqualTo(TileType.POND);
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When tiles are equal, returns true")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenTilesAreEqual_ThenReturnsTrue() {
            assertThat(pondTile.equals(pondTile)).isTrue();
        }

        @Test
        @DisplayName("When tiles are not equal, returns false")
        void equals_WhenTilesAreNotEqual_ThenReturnsFalse() {
            assertThat(pondTile.equals(tile)).isFalse();
        }

        @Test
        @DisplayName("When tiles is null, returns false")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenTileIsNull_ThenReturnsFalse() {
            assertThat(pondTile.equals(null)).isFalse();
        }

        @Test
        @DisplayName("When tiles are not of the same class, returns false")
        @SuppressWarnings("EqualsBetweenInconvertibleTypes")
        void equals_WhenTilesAreNotOfTheSameClass_ThenReturnsFalse() {
            assertThat(pondTile.equals("")).isFalse();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When tiles are equal, returns same hash code")
        void hashCode_WhenTilesAreEqual_ThenReturnsSameHashCode() {
            assertThat(pondTile).hasSameHashCodeAs(pondTile);
        }

        @Test
        @DisplayName("When tiles are not equal, returns different hash code")
        void hashCode_WhenTilesAreNotEqual_ThenReturnsDifferentHashCode() {
            assertThat(pondTile).doesNotHaveSameHashCodeAs(tile);
        }
    }

    @Nested
    @DisplayName("Method copy")
    class TestCopy {
        @Test
        @DisplayName("When tile is copied, returns a new tile")
        void copy_WhenTileIsCopied_ThenReturnsNewTile() {
            assertThat(tile.copy()).isNotSameAs(tile);
        }

        @Test
        @DisplayName("When tile is copied, returns a tile with the same type")
        void copy_WhenTileIsCopied_ThenReturnsTileWithSameType() {
            assertThat(tile.copy().getType()).isEqualTo(tile.getType());
        }
    }
}

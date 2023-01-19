package com.takenoko.layers.tile;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

class TileTest {
    private Tile tile;
    private Tile other;
    private Tile pondTile;

    @BeforeEach
    void setUp() {
        pondTile = new Tile(TileType.POND);
        tile = new Tile();
        other = new Tile();
    }

    @AfterEach
    void tearDown() {
        pondTile = null;
        tile = null;
        other = null;
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
    @DisplayName("Method setImprovement && getImprovement")
    class TestSetImprovementAndGetImprovement {
        @Test
        @DisplayName("When instantiated, improvement is null")
        void getImprovement_WhenCalled_ThenReturnsNull() {
            assertThat(tile.getImprovement()).isEmpty();
        }

        @Test
        @DisplayName("When improvement is set, getImprovement returns the improvement")
        void getImprovement_WhenImprovementIsSet_ThenReturnsImprovement() {
            tile.setImprovement(ImprovementType.FERTILIZER);
            assertThat(tile.getImprovement()).isNotEmpty();
            assertThat(tile.getImprovement()).contains(ImprovementType.FERTILIZER);
        }

        @Test
        @DisplayName("When improvement is already set, setImprovement throws an exception")
        void setImprovement_WhenImprovementIsAlreadySet_ThenThrowsException() {
            tile.setImprovement(ImprovementType.FERTILIZER);
            assertThat(tile.getImprovement()).isNotEmpty();
            assertThat(tile.getImprovement()).contains(ImprovementType.FERTILIZER);
            assertThatThrownBy(() -> tile.setImprovement(ImprovementType.FERTILIZER))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The tile already has an improvement");
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
        @DisplayName("When tiles have not the same type, returns false")
        void equals_WhenTilesHaveNotTheSameType_ThenReturnsFalse() {
            assertThat(pondTile.equals(tile)).isFalse();
        }

        @Test
        @DisplayName("When tiles have not the same improvement, returns false")
        void equals_WhenTilesHaveNotTheSameImprovement_ThenReturnsFalse() {
            other.setImprovement(ImprovementType.FERTILIZER);
            tile.setImprovement(ImprovementType.ENCLOSURE);
            assertThat(other).isNotEqualTo(tile);
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
        @DisplayName("When tiles have different improvements, returns different hash code")
        void hashCode_WhenTilesHaveDifferentImprovements_ThenReturnsDifferentHashCode() {
            other.setImprovement(ImprovementType.FERTILIZER);
            assertThat(tile).doesNotHaveSameHashCodeAs(other);
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

        @Test
        @DisplayName("When tile is copied, returns a tile with the same improvement")
        void copy_WhenTileIsCopied_ThenReturnsTileWithSameImprovement() {
            tile.setImprovement(ImprovementType.FERTILIZER);
            assertThat(tile.copy().getImprovement()).isEqualTo(tile.getImprovement());
        }
    }
}

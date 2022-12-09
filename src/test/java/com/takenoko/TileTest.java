package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

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
}

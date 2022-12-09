package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

class TileTest {
    private Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    @AfterEach
    void tearDown() {
        tile = null;
    }

    @Test
    @DisplayName("When tile is created, it is not a pond.")
    void getPond_WhenCalled_ReturnsFalse() {
        assertThat(tile.getType()).isNotEqualTo(TileType.POND);
    }
}

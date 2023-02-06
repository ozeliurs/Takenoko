package com.takenoko.layers.irrigation;

import com.takenoko.vector.PositionVector;
import org.jetbrains.annotations.NotNull;

/**
 * This record represents an irrigation channel Position
 *
 * @param leftTilePosition the position of the tile on the left of the irrigation channel
 * @param rightTilePosition the position of the tile on the right of the irrigation channel
 */
public record IrrigationChannelPosition(
        @NotNull PositionVector leftTilePosition, @NotNull PositionVector rightTilePosition) {
    public IrrigationChannelPosition {
        if (leftTilePosition.equals(rightTilePosition)) {
            throw new IllegalArgumentException(
                    "The irrigation channel positions cannot be the same");
        }
        if (leftTilePosition.distance(rightTilePosition) != 1) {
            throw new IllegalArgumentException("The irrigation channel position must be adjacent");
        }
    }
}

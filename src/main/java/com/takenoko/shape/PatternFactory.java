package com.takenoko.shape;

import com.takenoko.layers.tile.TileColor;

/** This class is a factory for the creation of patterns. */
public enum PatternFactory {
    LINE {
        @Override
        public Pattern createPattern(TileColor tileColor) {
            return new Line(tileColor);
        }
    },

    CURVE {
        @Override
        public Pattern createPattern(TileColor tileColor) {
            return new Curve(tileColor);
        }
    },

    TRIANGLE {
        @Override
        public Pattern createPattern(TileColor tileColor) {
            return new Triangle(tileColor);
        }
    },

    DIAMOND {
        @Override
        public Pattern createPattern(TileColor tileColor) {
            return new Diamond(tileColor);
        }
    },

    MIXED_COLORS_DIAMOND {
        @Override
        public Pattern createPattern(TileColor tileColor) {
            return new MixedColorsDiamond(tileColor);
        }
    };

    public abstract Pattern createPattern(TileColor tileColor);
}

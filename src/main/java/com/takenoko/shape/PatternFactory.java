package com.takenoko.shape;

/** This class is a factory for the creation of patterns. */
public enum PatternFactory {
    LINE {
        @Override
        public Pattern createPattern() {
            return new Line();
        }
    },

    CURVE {
        @Override
        public Pattern createPattern() {
            return new Curve();
        }
    },

    TRIANGLE {
        @Override
        public Pattern createPattern() {
            return new Triangle();
        }
    },

    DIAMOND {
        @Override
        public Pattern createPattern() {
            return new Diamond();
        }
    },

    MIXED_COLORS_DIAMOND {
        @Override
        public Pattern createPattern() {
            return new MixedColorsDiamond();
        }
    };

    public abstract Pattern createPattern();
}

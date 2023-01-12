package com.takenoko.shape;

/** This class is a factory for the creation of patterns. */
public enum PatternFactory {
    LINE {
        @Override
        public Pattern createPattern() {
            return new Line();
        }
    },

    ADJACENT {
        @Override
        public Pattern createPattern() {
            return new Adjacent();
        }
    };

    public abstract Pattern createPattern();
}

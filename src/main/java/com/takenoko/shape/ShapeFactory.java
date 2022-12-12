package com.takenoko.shape;

public enum ShapeFactory {
    LINE {
        @Override
        public Shape createShape() {
            return new Line();
        }
    },

    ADJACENT {
        @Override
        public Shape createShape() {
            return new Adjacent();
        }
    };

    public abstract Shape createShape();
}

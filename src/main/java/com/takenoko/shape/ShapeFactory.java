package com.takenoko.shape;

public enum ShapeFactory {
    LINE {
        @Override
        public Shape createShape() {
            return new Line();
        }
    };

    public abstract Shape createShape();
}

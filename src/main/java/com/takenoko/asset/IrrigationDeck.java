package com.takenoko.asset;

/** IrrigationDeck represents the irrigation deck, defaults to 20 irrigation. */
public class IrrigationDeck {
    private static final int DEFAULT_SIZE = 20;
    private int size;

    /** Creates a new IrrigationDeck with the default size. */
    public IrrigationDeck() {
        size = DEFAULT_SIZE;
    }

    /** Draws an irrigation from the deck. */
    public void draw() {
        if (size == 0) {
            throw new IllegalArgumentException("No more irrigation");
        }
        size--;
    }

    /**
     * @return true if the deck has irrigation, false otherwise
     */
    public boolean hasIrrigation() {
        return size > 0;
    }

    /**
     * @return the number of irrigation left in the deck
     */
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IrrigationDeck that = (IrrigationDeck) o;

        return size == that.size;
    }

    @Override
    public int hashCode() {
        return size;
    }

    @Override
    public String toString() {
        return "IrrigationDeck{" + "size=" + size + '}';
    }
}

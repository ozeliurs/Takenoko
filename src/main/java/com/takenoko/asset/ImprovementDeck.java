package com.takenoko.asset;

import com.takenoko.layers.tile.ImprovementType;
import java.util.HashMap;

/** The deck containing all the improvement chips you can place on the tiles. */
public class ImprovementDeck extends HashMap<ImprovementType, Integer> {
    public ImprovementDeck() {
        for (ImprovementType improvementType : ImprovementType.values()) {
            this.put(improvementType, 3);
        }
    }

    /**
     * @param improvementType what improvement type you choose to draw
     * @return the improvement type you choose to draw
     */
    public ImprovementType draw(ImprovementType improvementType) {
        if (this.get(improvementType) > 0) {
            this.put(improvementType, this.get(improvementType) - 1);
            return improvementType;
        }

        throw new IllegalArgumentException("No more improvement of type " + improvementType);
    }

    /**
     * @param improvementType the improvement type you want to know if available
     * @return true if the improvement type is available, false otherwise
     */
    public boolean hasImprovement(ImprovementType improvementType) {
        return this.get(improvementType) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImprovementDeck)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

package com.takenoko.layers.tile;

public enum ImprovementType {
    /**
     * The fertilizer 🌱 increases the growth of the bamboo in the plot where it is located. Each
     * time the bamboo grows, two sections are added instead of one (within the limit of maximum of
     * 4 sections).
     */
    FERTILIZER,
    /**
     * The enclosure 🛑 protects the bamboo of the plot where it is, the panda can cross it, stop
     * there but in no case eat the bamboo that is there.
     */
    ENCLOSURE,
    ANY,
    NONE, POOL,
}

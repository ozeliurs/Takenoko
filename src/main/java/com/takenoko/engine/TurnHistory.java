package com.takenoko.engine;

import java.util.ArrayList;
import java.util.Arrays;

public class TurnHistory extends ArrayList<HistoryItem> {

    public TurnHistory(HistoryItem... items) {
        this.addAll(Arrays.asList(items));
    }
}

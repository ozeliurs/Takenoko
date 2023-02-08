package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.objective.Objective;
import java.util.List;

public record HistoryItem(Action action, List<Objective> redeemedObjectives) {}

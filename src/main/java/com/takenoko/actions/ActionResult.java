package com.takenoko.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the result of an action.
 *
 * @param availableActions the availableActions available to the bot after the action
 * @param cost the cost of the action
 */
public record ActionResult(List<Class<? extends Action>> availableActions, int cost) {
    static List<Class<? extends Action>> DEFAULT_AVAILABLE_ACTIONS =
            List.of(MovePandaAction.class, MoveGardenerAction.class, PlaceTileAction.class);

    public ActionResult(List<Class<? extends Action>> availableActions) {
        this(availableActions, 0);
    }

    public ActionResult() {
        this(new ArrayList<>(DEFAULT_AVAILABLE_ACTIONS), 0);
    }

    public ActionResult(int cost) {
        this(new ArrayList<>(DEFAULT_AVAILABLE_ACTIONS), cost);
    }
}

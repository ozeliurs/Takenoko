package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class PriorityBot extends HashMap<Action, Integer> implements Bot {

    ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();

    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        Optional<Action> action =
                this.entrySet().stream()
                        // Remove actions that are not available
                        .filter(v -> botState.getAvailableActions().contains(v.getKey().getClass()))
                        // Order by priority
                        .max(Comparator.comparingInt(Entry::getValue))
                        // Get the action with the highest priority
                        .map(Map.Entry::getKey);

        this.clear();

        consoleUserInterface.displayDebug("PriorityBot chose action: " + action);

        return action.orElseGet(
                () -> {
                    consoleUserInterface.displayMessage(
                            "No actions defined with priority for this bot, using FullRandomBot");
                    return new FullRandomBot().chooseAction(board, botState, history);
                });
    }

    protected void addActionIfNotNull(Action action, int priority) {
        if (action != null) {
            this.put(action, priority);
        }
    }
}

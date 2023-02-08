package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.*;

public abstract class PriorityBot extends HashMap<Action, Double> implements Bot {

    public static final int DEFAULT_PRIORITY = 0;
    transient ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();

    protected abstract void fillAction(Board board, BotState botState, History history);

    public PriorityBot compute(Board board, BotState botState, History history) {
        fillAction(board, botState, history);
        return this;
    }

    public Action chooseAction(Board board, BotState botState, History history) {
        fillAction(board, botState, history);
        // filtered = this.entrySet().stream()
        // Remove actions that are not available
        //        .filter(v -> botState.getAvailableActions().contains(v.getKey().getClass()))

        Optional<Action> action =
                this.entrySet().stream()
                        // Remove actions that are not available
                        .filter(v -> botState.getAvailableActions().contains(v.getKey().getClass()))
                        // Order by priority
                        .min(Comparator.comparingDouble(Entry::getValue))
                        // Get the action with the highest priority
                        .map(Map.Entry::getKey);

        consoleUserInterface.displayDebug(botState.getAvailableActions() + "");

        consoleUserInterface.displayDebug("PriorityBot has these actions: " + this);

        consoleUserInterface.displayDebug("PriorityBot chose action: " + action);
        clear();
        return action.orElseGet(
                () -> {
                    consoleUserInterface.displayMessage(
                            "No actions defined with priority for this bot, using FullRandomBot");
                    return new FullRandomBot().chooseAction(board, botState, history);
                });
    }

    protected void addActionWithPriority(Action action, double priority) {
        if (action != null) {
            this.put(action, priority);
        }
    }

    protected void add(PriorityBot bot) {
        this.addWithOffset(bot, 0);
    }

    /** This method is used to add a bot with an offset */
    protected void addWithOffset(PriorityBot bot, double offset) {
        bot.forEach(
                (k, v) -> {
                    consoleUserInterface.displayDebug(
                            "Adding action " + k + " with priority " + (v + offset));
                    this.put(k, v + offset);
                });
    }

    /** This method is used to add a bot with a Linear function offset */
    protected void addWithLinear(PriorityBot bot, double multiplier) {
        bot.forEach((k, v) -> this.put(k, v + multiplier));
    }

    /** This method is used to add a bot with Affine function offset */
    protected void addWithAffine(PriorityBot bot, double multiplier, double offset) {
        bot.forEach((k, v) -> this.put(k, v * multiplier + offset));
    }

    /** This method is used to add a bot and squash the priority between two numbers */
    protected void addWithSquash(PriorityBot bot, double min, double max) {
        double minPriority = bot.values().stream().min(Double::compareTo).orElse(0.0);
        double maxPriority = bot.values().stream().max(Double::compareTo).orElse(0.0);
        double range = maxPriority - minPriority;
        bot.forEach(
                (k, v) -> {
                    double priority = (v - minPriority) / range;
                    this.put(k, min + priority * (max - min));
                });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PriorityBot that = (PriorityBot) o;
        return Objects.equals(consoleUserInterface, that.consoleUserInterface);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), consoleUserInterface);
    }
}

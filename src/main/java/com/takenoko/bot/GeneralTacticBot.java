package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.tile.PlaceTileWithImprovementAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.bot.unitary.*;
import com.takenoko.engine.*;
import java.util.*;

public class GeneralTacticBot extends PriorityBot {
    private final Map<Class<? extends Action>, Integer> priorityByActionClass;

    public GeneralTacticBot(Map<Class<? extends Action>, Integer> priorityByActionClass) {
        super();
        this.priorityByActionClass = priorityByActionClass;
    }

    @SuppressWarnings({"java:S3599", "java:S1171"})
    public GeneralTacticBot() {
        this(
                new HashMap<>() {
                    {
                        put(PlaceIrrigationFromInventoryAction.class, 10);
                        put(PlaceTileAction.class, 9);
                        put(PlaceTileWithImprovementAction.class, 9);
                        put(ChooseAndApplyWeatherAction.class, 8);
                        put(ChooseIfApplyWeatherAction.class, 7);
                        put(StoreIrrigationInInventoryAction.class, 5);
                        put(RedeemObjectiveAction.class, 4);
                        put(DrawIrrigationAction.class, 3);
                        put(MovePandaAction.class, 2);
                        put(MoveGardenerAction.class, 1);
                    }
                });
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // We do not care if action is available or not, the unavailable actions will be removed by
        // the super method.

        this.add(
                new IrrigationMaster(
                                priorityByActionClass.get(PlaceIrrigationFromInventoryAction.class),
                                priorityByActionClass.get(DrawIrrigationAction.class),
                                priorityByActionClass.get(StoreIrrigationInInventoryAction.class))
                        .compute(board, botState, history));

        this.add(
                new WeatherMaster(
                                priorityByActionClass.get(ChooseIfApplyWeatherAction.class),
                                priorityByActionClass.get(ChooseAndApplyWeatherAction.class))
                        .compute(board, botState, history));

        this.addWithOffset(
                new SmartPanda().compute(board, botState, history),
                priorityByActionClass.get(MovePandaAction.class));

        this.addWithOffset(
                new SmartGardener().compute(board, botState, history),
                priorityByActionClass.get(MoveGardenerAction.class));

        this.addWithOffset(
                new SmartPattern().compute(board, botState, history),
                priorityByActionClass.get(PlaceTileAction.class));

        this.addWithOffset(
                new SmartDrawIrrigation().compute(board, botState, history),
                priorityByActionClass.get(DrawIrrigationAction.class));

        this.addWithOffset(
                new SmartObjective().compute(board, botState, history),
                priorityByActionClass.get(RedeemObjectiveAction.class));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GeneralTacticBot that = (GeneralTacticBot) o;
        return Objects.equals(priorityByActionClass, that.priorityByActionClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), priorityByActionClass);
    }
}

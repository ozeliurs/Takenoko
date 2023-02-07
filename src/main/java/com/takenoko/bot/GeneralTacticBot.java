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
import com.takenoko.objective.PatternObjective;
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
                        put(ChooseIfApplyWeatherAction.class, 8);
                        put(ChooseAndApplyWeatherAction.class, 7);
                        put(StoreIrrigationInInventoryAction.class, 5);
                        put(RedeemObjectiveAction.class, 4);
                        put(DrawIrrigationAction.class, 3);
                        put(MovePandaAction.class, 2);
                        put(MoveGardenerAction.class, 1);
                    }
                });
    }

    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        // We do not care if action is available or not, the unavailable actions will be removed by
        // the super method.

        System.out.println("GeneralTacticBot.chooseAction");

        this.add(
                new IrrigationMaster(
                        priorityByActionClass.get(PlaceIrrigationFromInventoryAction.class),
                        priorityByActionClass.get(DrawIrrigationAction.class),
                        priorityByActionClass.get(StoreIrrigationInInventoryAction.class)));

        System.out.println("GeneralTacticBot.chooseAction 1");

        this.add(
                new WeatherMaster(
                        priorityByActionClass.get(ChooseIfApplyWeatherAction.class),
                        priorityByActionClass.get(ChooseAndApplyWeatherAction.class)));

        System.out.println("GeneralTacticBot.chooseAction 2");

        this.addWithOffset(new RushPandaBot(), priorityByActionClass.get(MovePandaAction.class));

        System.out.println("GeneralTacticBot.chooseAction 3");

        this.addWithOffset(
                new SmartGardener(), priorityByActionClass.get(MoveGardenerAction.class));

        System.out.println("GeneralTacticBot.chooseAction 4");

        this.addWithOffset(new SmartPattern(), priorityByActionClass.get(PatternObjective.class));

        System.out.println("GeneralTacticBot.chooseAction 5");

        this.addWithOffset(
                new SmartDrawIrrigation(), priorityByActionClass.get(DrawIrrigationAction.class));

        System.out.println("GeneralTacticBot.chooseAction 6");

        this.addWithOffset(
                new SmartObjective(), priorityByActionClass.get(RedeemObjectiveAction.class));

        System.out.println("GeneralTacticBot.chooseAction 7");

        return super.chooseAction(board, botState, history);
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

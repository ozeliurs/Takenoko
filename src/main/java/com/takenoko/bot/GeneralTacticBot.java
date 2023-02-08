package com.takenoko.bot;

import com.takenoko.bot.unitary.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GeneralTacticBot extends PriorityBot {
    private final Map<String, Integer> parameters;

    public GeneralTacticBot(Map<String, Integer> priorityByActionClass) {
        super();
        this.parameters = priorityByActionClass;
    }

    @SuppressWarnings({"java:S3599", "java:S1171"})
    public GeneralTacticBot() {
        this(
                new HashMap<>() {
                    {
                        put("SmartIrrigationPlacing", 10); // 3
                        put("SmartPatternPlacing", 9); // 6
                        put("ChooseAndApplyWeather", 8); // 1
                        put("ChooseIfApplyWeather", 7); // 2
                        put("StoreIrrigationInInventory", 5); // 5
                        put("RedeemObjective", 4); // 8
                        put("DrawIrrigation", 3); // 4
                        put("MovePanda", 2); // 7
                        put("MoveGardener", 1); // 4
                    }
                });
    }

    @Override
    @SuppressWarnings({"java:S3599", "java:S1171"})
    protected void fillAction(Board board, BotState botState, History history) {
        // We do not care if action is available or not, the unavailable actions will be removed by
        // the super method.

        this.add(
                new IrrigationMaster(
                                parameters.get("SmartIrrigationPlacing"),
                                parameters.get("DrawIrrigation"),
                                parameters.get("StoreIrrigationInInventory"))
                        .compute(board, botState, history));

        this.add(
                new WeatherMaster(
                                parameters.get("ChooseIfApplyWeather"),
                                parameters.get("ChooseAndApplyWeather"))
                        .compute(board, botState, history));

        this.addWithOffset(
                new SmartPanda().compute(board, botState, history), parameters.get("MovePanda"));

        this.addWithOffset(
                new SmartGardener().compute(board, botState, history),
                parameters.get("MoveGardener"));

        this.addWithOffset(
                new SmartPattern().compute(board, botState, history),
                parameters.get("SmartPatternPlacing"));

        this.addWithOffset(
                new SmartObjective().compute(board, botState, history),
                parameters.get("RedeemObjective"));

        this.addWithOffset(
                new SmartDrawImprovement(
                                Map.of(
                                        ImprovementType.WATERSHED, 4,
                                        ImprovementType.ENCLOSURE, 1))
                        .compute(board, botState, history),
                0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GeneralTacticBot that = (GeneralTacticBot) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parameters);
    }
}

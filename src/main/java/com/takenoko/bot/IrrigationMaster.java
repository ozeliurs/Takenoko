package com.takenoko.bot;

import com.takenoko.bot.unitary.SmartDrawIrrigation;
import com.takenoko.bot.unitary.SmartPlaceIrrigation;
import com.takenoko.bot.unitary.SmartStoreIrrigation;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import java.util.Map;
import java.util.Objects;

public class IrrigationMaster extends PriorityBot {
    private final Map<String, Integer> params;

    static final Map<String, Integer> DEFAULT_PARAMS =
            Map.of(
                    "placeIrrigationCoefficient", 1,
                    "drawIrrigationCoefficient", 1,
                    "storeIrrigationCoefficient", 1,
                    "maxIrrigationToChooseToDraw", 5);

    public IrrigationMaster(Map<String, Integer> params) {
        this.params = params;
    }

    public IrrigationMaster() {
        this.params = DEFAULT_PARAMS;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        this.addWithOffset(
                new SmartPlaceIrrigation().compute(board, botState, history),
                params.get("placeIrrigationCoefficient"));
        this.addWithOffset(
                new SmartDrawIrrigation(params.get("maxIrrigationToChooseToDraw"))
                        .compute(board, botState, history),
                params.get("drawIrrigationCoefficient"));
        this.addWithOffset(
                new SmartStoreIrrigation().compute(board, botState, history),
                params.get("storeIrrigationCoefficient"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IrrigationMaster that = (IrrigationMaster) o;
        return Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), params);
    }
}

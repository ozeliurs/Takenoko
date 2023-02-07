package com.takenoko.bot;

import com.takenoko.bot.unitary.SmartDrawIrrigation;
import com.takenoko.bot.unitary.SmartPlaceIrrigation;
import com.takenoko.bot.unitary.SmartStoreIrrigation;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import java.util.Objects;

public class IrrigationMaster extends PriorityBot {
    private final int placeIrrigationCoefficient;
    private final int drawIrrigationCoefficient;
    private final int storeIrrigationCoefficient;

    public IrrigationMaster(
            int placeIrrigationCoefficient,
            int drawIrrigationCoefficient,
            int storeIrrigationCoefficient) {
        super();
        this.placeIrrigationCoefficient = placeIrrigationCoefficient;
        this.drawIrrigationCoefficient = drawIrrigationCoefficient;
        this.storeIrrigationCoefficient = storeIrrigationCoefficient;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        this.addWithOffset(
                new SmartPlaceIrrigation().compute(board, botState, history),
                placeIrrigationCoefficient);
        this.addWithOffset(
                new SmartDrawIrrigation().compute(board, botState, history),
                drawIrrigationCoefficient);
        this.addWithOffset(
                new SmartStoreIrrigation().compute(board, botState, history),
                storeIrrigationCoefficient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IrrigationMaster that = (IrrigationMaster) o;
        return placeIrrigationCoefficient == that.placeIrrigationCoefficient
                && drawIrrigationCoefficient == that.drawIrrigationCoefficient
                && storeIrrigationCoefficient == that.storeIrrigationCoefficient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                placeIrrigationCoefficient,
                drawIrrigationCoefficient,
                storeIrrigationCoefficient);
    }
}

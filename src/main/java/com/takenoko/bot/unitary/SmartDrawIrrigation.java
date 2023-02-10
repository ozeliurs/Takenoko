package com.takenoko.bot.unitary;

import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import java.util.Objects;

public class SmartDrawIrrigation extends PriorityBot {

    private final int maxIrrigationToChooseToDraw;

    public SmartDrawIrrigation(int maxIrrigationToChooseToDraw) {
        this.maxIrrigationToChooseToDraw = maxIrrigationToChooseToDraw;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        if (botState.getInventory().getIrrigationChannelsCount() < maxIrrigationToChooseToDraw) {
            this.addActionWithPriority(new DrawIrrigationAction(), DEFAULT_PRIORITY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartDrawIrrigation that = (SmartDrawIrrigation) o;
        return maxIrrigationToChooseToDraw == that.maxIrrigationToChooseToDraw;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxIrrigationToChooseToDraw);
    }
}

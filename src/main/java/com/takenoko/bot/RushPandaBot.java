package com.takenoko.bot;

import com.takenoko.actions.improvement.DrawImprovementAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import java.util.HashMap;
import java.util.Objects;

public class RushPandaBot extends GeneralTacticBot {
    private final transient FullRandomBot randomBot;

    @SuppressWarnings({"java:S3599", "java:S1171"})
    public RushPandaBot() {
        super(
                new HashMap<>() {
                    {
                        put("MovePanda", 10);
                        put("MoveGardener", -1);
                        put("RedeemObjective", -1);
                        put("SmartPatternPlacing", -1);
                        put("ChooseAndApplyWeather", -1);
                        put("ChooseIfApplyWeather", -1);
                        put("DrawIrrigation", -1);
                        put("StoreIrrigationInInventory", -1);
                        put("SmartIrrigationPlacing", -1);
                    }
                });
        this.randomBot = new FullRandomBot();
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        super.fillAction(board, botState, history);
        this.addActionWithPriority(
                this.randomBot.getRandomForcedMovePandaAction(board), DEFAULT_PRIORITY);
        this.addActionWithPriority(
                this.randomBot.getRandomMovePandaAction(board), DEFAULT_PRIORITY);

        this.addActionWithPriority(new DrawImprovementAction(ImprovementType.FERTILIZER), 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RushPandaBot that = (RushPandaBot) o;
        return Objects.equals(randomBot, that.randomBot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), randomBot);
    }
}

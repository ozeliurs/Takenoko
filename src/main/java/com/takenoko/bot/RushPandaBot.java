package com.takenoko.bot;

import static com.takenoko.bot.utils.pathfinding.panda.PandaPathfinding.getPandaMovesThatEatBamboo;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.improvement.ApplyImprovementAction;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
import com.takenoko.actions.improvement.DrawImprovementAction;
import com.takenoko.bot.unitary.SmartDrawImprovement;
import com.takenoko.bot.unitary.SmartPanda;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RushPandaBot extends PriorityBot {
    private final transient FullRandomBot randomBot;

    public RushPandaBot() {
        super();
        this.randomBot = new FullRandomBot();
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {

        this.addWithOffset((new SmartPanda()).compute(board, botState, history), 50);

        this.addWithOffset(
                (new SmartDrawImprovement(Map.of(ImprovementType.FERTILIZER, 1)))
                        .compute(board, botState, history),
                50);

        if (botState.getInventory().hasImprovement(ImprovementType.FERTILIZER)
                && botState.getAvailableActions().contains(ApplyImprovementAction.class)
                && !board.getAvailableImprovementPositions().isEmpty()) {
            this.addActionWithPriority(
                    new ApplyImprovementFromInventoryAction(
                            ImprovementType.FERTILIZER,
                            board.getAvailableImprovementPositions().get(0)),
                    2);
        }

        List<PositionVector> pandaMoveThatEatBamboo = getPandaMovesThatEatBamboo(board);

        if (!pandaMoveThatEatBamboo.isEmpty()) {
            this.addActionWithPriority(new MovePandaAction(pandaMoveThatEatBamboo.get(0)), 100);
            this.addActionWithPriority(
                    new ForcedMovePandaAction(pandaMoveThatEatBamboo.get(0)), 100);
        }
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

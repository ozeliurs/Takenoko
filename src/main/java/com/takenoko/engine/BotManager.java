package com.takenoko.engine;

import com.takenoko.player.Action;
import com.takenoko.player.Bot;

public class BotManager extends PlayableManager {
    private final Bot bot;

    public BotManager(Bot bot) {
        super();
        this.bot = bot;
    }

    public void playBot(Board board) {
        for (int i = 0; i < this.getNumberOfRounds(); i++) {
            displayMessage(board.getActorsManager().getPanda().positionMessage());
            Action action = bot.chooseAction(board);
            action.execute(board, this);
            verifyObjective(board);
            if (isObjectiveAchieved()) {
                return;
            }
        }
    }
}

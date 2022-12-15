package com.takenoko.engine;

import com.takenoko.player.Action;
import com.takenoko.player.Playable;

public class BotManager extends PlayableManager {
    private final Playable playable;

    public BotManager(Playable playable) {
        super();
        this.playable = playable;
    }

    public void playBot(Board board) {
        for (int i = 0; i < this.getNumberOfRounds(); i++) {
            displayMessage(board.getActorsManager().getPanda().positionMessage());
            Action action = playable.chooseAction(board);
            action.execute(board, this);
            verifyObjective(board);
            if (isObjectiveAchieved()) {
                return;
            }
        }
    }
}

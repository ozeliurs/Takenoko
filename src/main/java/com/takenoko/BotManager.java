package com.takenoko;

import com.takenoko.vector.Vector;
import org.apache.commons.lang3.tuple.Pair;

public class BotManager extends PlayableManager {
    private final Bot bot;

    public BotManager(Bot bot) {
        super();
        this.bot = bot;
    }

    public void playBot(Board board) {
        for (int i = 0; i < this.getNumberOfActions(); i++) {
            Pair<Vector, Tile> botChoice =
                    bot.chooseTileToPlace(
                            board.getAvailableTiles(), board.getAvailableTilePositions());
            board.placeTile(botChoice.getRight(), botChoice.getLeft());
            displayMessage("The bot has placed a tile at " + botChoice.getLeft());
        }

        verifyObjective(board);

        if (this.objectiveIsAchieved()) {
            displayMessage("The bot has completed the objective!");
        }
    }
}

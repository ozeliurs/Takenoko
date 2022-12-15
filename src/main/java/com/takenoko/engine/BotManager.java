package com.takenoko.engine;

import com.takenoko.layers.Board;
import com.takenoko.player.Bot;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import org.apache.commons.lang3.tuple.Pair;

public class BotManager extends PlayableManager {
    private final Bot bot;

    public BotManager(Bot bot) {
        super();
        this.bot = bot;
    }

    public void playBot(Board board) {
        for (int i = 0; i < this.getNumberOfRounds(); i++) {
            displayMessage(board.getPanda().positionMessage());
            Pair<PositionVector, Tile> botChoice =
                    bot.chooseTileToPlace(
                            board.getTileLayer().getAvailableTiles(),
                            board.getTileLayer().getAvailableTilePositions());
            board.getTileLayer().placeTile(botChoice.getRight(), botChoice.getLeft());
            displayMessage("The bot has placed a tile at " + botChoice.getLeft());

            verifyObjective(board);
            if (isObjectiveAchieved()) {
                return;
            }
        }
    }
}

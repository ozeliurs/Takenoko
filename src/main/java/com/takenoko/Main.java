package com.takenoko;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.takenoko.bot.FullRandomBot;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.GameEngine;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;

public class Main {
    @Parameter(
            names = {"--2thousands"},
            description =
                    "Run 2*1000 games: best_Bot VS second_best_Bot && best_Bot VS best_Bot | no"
                            + " logs")
    private boolean thousands = false;

    @Parameter(
            names = {"--demo"},
            description = "Run one game as Demo | show all logs")
    private boolean demo = false;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        main.run();
    }

    public void run() {
        GameEngine gameEngine = new GameEngine();
        if (thousands) {
            gameEngine.runGame(10, Level.FATAL);
            GameEngine gameEngine2 =
                    new GameEngine(
                            new ArrayList<>(
                                    List.of(
                                            new BotManager(new FullRandomBot(), "Bob2"),
                                            new BotManager(new FullRandomBot(), "Joe2"))));
            gameEngine2.runGame(10, Level.FATAL);
        } else if (demo) {
            gameEngine.getConsoleUserInterface().getLogger().setLevel(Level.INFO);
            new GameEngine().runGame();
        } else {
            gameEngine.getConsoleUserInterface().displayMessage("no parameters");
        }
    }
}

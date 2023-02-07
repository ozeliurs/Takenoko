package com.takenoko.bot;

import java.util.HashMap;

public class RushPandaBot extends GeneralTacticBot {
    @SuppressWarnings({"java:S3599", "java:S1171"})
    public RushPandaBot() {
        super(
                new HashMap<>() {
                    {
                        put("MovePanda", 10);
                        put("MoveGardener", 9);
                        put("RedeemObjective", 8);
                        put("SmartPatternPlacing", 7);
                        put("ChooseAndApplyWeather", 5);
                        put("ChooseIfApplyWeather", 4);
                        put("DrawIrrigation", 3);
                        put("StoreIrrigationInInventory", 2);
                        put("SmartIrrigationPlacing", 1);
                    }
                });
    }
}

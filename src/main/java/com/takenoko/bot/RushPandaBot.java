package com.takenoko.bot;

import java.util.HashMap;

public class RushPandaBot extends GeneralTacticBot {
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
    }
}

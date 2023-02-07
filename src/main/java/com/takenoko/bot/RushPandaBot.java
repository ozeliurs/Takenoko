package com.takenoko.bot;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.tile.PlaceTileWithImprovementAction;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import java.util.HashMap;

public class RushPandaBot extends GeneralTacticBot {
    @SuppressWarnings({"java:S3599", "java:S1171"})
    public RushPandaBot() {
        super(
                new HashMap<>() {
                    {
                        put(MovePandaAction.class, 10);
                        put(MoveGardenerAction.class, 9);
                        put(RedeemObjectiveAction.class, 8);
                        put(PlaceTileAction.class, 7);
                        put(PlaceTileWithImprovementAction.class, 6);
                        put(ChooseAndApplyWeatherAction.class, 5);
                        put(ChooseIfApplyWeatherAction.class, 4);
                        put(DrawIrrigationAction.class, 3);
                        put(StoreIrrigationInInventoryAction.class, 2);
                        put(PlaceIrrigationFromInventoryAction.class, 1);
                    }
                });
    }
}

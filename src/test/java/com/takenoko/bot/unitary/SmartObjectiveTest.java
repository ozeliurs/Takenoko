package com.takenoko.bot.unitary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SmartObjectiveTest {
    @Nested
    @DisplayName("method fillAction")
    class FillAction {
        @Test
        @DisplayName("Should redeem a PandaObjective if he has more than one in inventory")
        void shouldRedeemPandaObjective() {}

        @Test
        @DisplayName("Shoud not any objective if it doesn't make us win")
        void shouldNotRedeemObjective() {}
    }
}

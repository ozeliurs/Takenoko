package com.takenoko.actions.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Defines if the action can be played multiple times per turn. */
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionCanBePlayedMultipleTimesPerTurn {}

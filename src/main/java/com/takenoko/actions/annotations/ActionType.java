package com.takenoko.actions.annotations;

/** Types of actions that can be performed. This used to define the lifetime of an action */
public enum ActionType {
    /** This action is the one a bot should choose in priority, these actions are not persistent */
    FORCED,
    /** This action in an action that will last in time until it is used. */
    PERSISTENT,
    /** This action is available on each turn. */
    DEFAULT
}

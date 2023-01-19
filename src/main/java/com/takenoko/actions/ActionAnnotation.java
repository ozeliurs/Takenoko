package com.takenoko.actions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Annotation to define the type of action lifetime. */
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionAnnotation {
    ActionType value();
}

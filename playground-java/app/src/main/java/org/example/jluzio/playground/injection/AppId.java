package org.example.jluzio.playground.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by jluzio on 15/03/2018.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface AppId {
}

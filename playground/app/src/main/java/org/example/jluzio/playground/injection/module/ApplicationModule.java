package org.example.jluzio.playground.injection.module;

import org.example.jluzio.playground.injection.AppId;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jluzio on 15/03/2018.
 */

@Module
public class ApplicationModule {

    @Provides
    @AppId
    String appId() {
        return "Playground@1.0";
    }

}

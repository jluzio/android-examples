package org.example.jluzio.playground.injection.component;


import android.app.Application;

import org.example.jluzio.playground.PlaygroundApplication;
import org.example.jluzio.playground.injection.module.ActivityModule;
import org.example.jluzio.playground.injection.module.AppModule;
import org.example.jluzio.playground.injection.module.ServicesModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by jluzio on 15/03/2018.
 */

@Singleton
@Component(modules = {AppModule.class, ActivityModule.class, ServicesModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(PlaygroundApplication application);
}

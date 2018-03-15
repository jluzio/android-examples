package org.example.jluzio.playground.injection.component;


import org.example.jluzio.playground.Application;
import org.example.jluzio.playground.injection.module.ActivityModule;
import org.example.jluzio.playground.injection.module.ApplicationModule;

import dagger.Component;

/**
 * Created by jluzio on 15/03/2018.
 */

@Component(modules = {ApplicationModule.class, ActivityModule.class})
public interface ApplicationComponent {
    void inject(Application application);
}

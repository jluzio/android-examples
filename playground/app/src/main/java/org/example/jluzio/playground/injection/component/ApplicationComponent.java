package org.example.jluzio.playground.injection.component;


import org.example.jluzio.playground.Application;
import org.example.jluzio.playground.injection.module.ApplicationModule;
import org.example.jluzio.playground.injection.module.ArchComponentsActivityModule;

import dagger.Component;

/**
 * Created by jluzio on 15/03/2018.
 */

@Component(modules = {ApplicationModule.class, ArchComponentsActivityModule.class})
public interface ApplicationComponent {
    void inject(Application application);
}

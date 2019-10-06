package org.example.jluzio.playground.injection.module;

import org.example.jluzio.playground.ui.samples.ArchComponentsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jluzio on 15/03/2018.
 */

@Module
public abstract class ActivityModule {

    /**
     * Alternatively use the complete way to inject activities
     * Check files:
     *  - ArchComponentsActivitySubcomponent.java.txt
     *  - ArchComponentsActivityModule.java.txt
     * @see <a href="https://google.github.io/dagger/android#injecting-activity-objects">documentation</a>
     * @return
     */
    @ContributesAndroidInjector
    abstract ArchComponentsActivity contributeArchComponentsActivityInjector();

}
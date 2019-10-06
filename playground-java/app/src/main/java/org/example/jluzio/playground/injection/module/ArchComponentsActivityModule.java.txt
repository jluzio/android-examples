package org.example.jluzio.playground.injection.module;

import android.app.Activity;

import org.example.jluzio.playground.injection.component.ArchComponentsActivitySubcomponent;
import org.example.jluzio.playground.ui.samples.ArchComponentsActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by jluzio on 15/03/2018.
 */

@Module(subcomponents = ArchComponentsActivitySubcomponent.class)
public abstract class ArchComponentsActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(ArchComponentsActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(ArchComponentsActivitySubcomponent.Builder builder);
}
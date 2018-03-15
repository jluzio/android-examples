package org.example.jluzio.playground.injection.component;

import org.example.jluzio.playground.ui.samples.ArchComponentsActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by jluzio on 15/03/2018.
 */

@Subcomponent
//@Subcomponent(modules = YourActivityModule.class)
public interface ArchComponentsActivitySubcomponent extends AndroidInjector<ArchComponentsActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ArchComponentsActivity> {
    }
}
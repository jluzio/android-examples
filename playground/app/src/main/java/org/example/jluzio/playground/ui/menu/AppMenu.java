package org.example.jluzio.playground.ui.menu;

import android.view.Menu;

import org.example.jluzio.playground.ui.course.ButtonCounterChallengeActivity;
import org.example.jluzio.playground.ui.course.CalculatorActivity;
import org.example.jluzio.playground.ui.course.Challenge20171127x01Activity;
import org.example.jluzio.playground.ui.course.Challenge20171128x01Activity;
import org.example.jluzio.playground.ui.course.rssFeed.RssFeedActivity;
import org.example.jluzio.playground.ui.menu.simple.MenuDef;
import org.example.jluzio.playground.ui.menu.simple.MenuItemDef;
import org.example.jluzio.playground.ui.menu.simple.Menus;
import org.example.jluzio.playground.ui.menu.simple.Sequence;
import org.example.jluzio.playground.ui.samples.ArchComponentsActivity;
import org.example.jluzio.playground.ui.samples.ReceiveParamIntentActivity;
import org.example.jluzio.playground.ui.samples.SampleActivity;
import org.example.jluzio.playground.ui.samples.SampleByScreenSizeActivity;
import org.example.jluzio.playground.ui.samples.SampleFragmentActivity;
import org.example.jluzio.playground.ui.samples.youtube.PlayerViewDemoActivity;

public class AppMenu {
    private static final AppMenu INSTANCE = new AppMenu();
    private MenuItemDef menuRoot = createMenu();

    public static AppMenu instance() {
        return INSTANCE;
    }

    private AppMenu() {}


    public MenuItemDef createMenu() {
        MenuDef courseMenuGroup = MenuDef.create("Course");
        courseMenuGroup
                .add(MenuItemDef.create(Challenge20171127x01Activity.class))
                .add(MenuItemDef.create(Challenge20171128x01Activity.class))
                .add(MenuItemDef.create(ButtonCounterChallengeActivity.class))
                .add(MenuItemDef.create(CalculatorActivity.class))
                .add(MenuItemDef.create(RssFeedActivity.class))
                .add(MenuItemDef.create(PlayerViewDemoActivity.class))
        ;

        MenuDef samplesMenuGroup = MenuDef.create("Samples");
        samplesMenuGroup
                .add(MenuItemDef.create(SampleActivity.class))
                .add(MenuItemDef.create(SampleByScreenSizeActivity.class))
                .add(MenuItemDef.create(ArchComponentsActivity.class))
                .add(MenuItemDef.create(SampleFragmentActivity.class))
                .add(MenuItemDef.create(ReceiveParamIntentActivity.class))
        ;

        MenuDef menuRoot = new MenuDef();
        menuRoot.setId(Menu.NONE);
        menuRoot
                .add(courseMenuGroup)
                .add(samplesMenuGroup);

        Menus.assignMissingIds(menuRoot, new Sequence());

        return menuRoot;
    }

    public MenuItemDef getMenuRoot() {
        return menuRoot;
    }
}

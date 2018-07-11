package org.example.jluzio.playground.ui.menu.simple;

import com.google.common.collect.Lists;

import java.util.List;

public class MenuDef extends MenuItemDef {
    private List<MenuItemDef> menuItems = Lists.newArrayList();

    public MenuDef() {
    }

    public MenuDef(Integer id, String name, Class<?> activityClass, List<MenuItemDef> menuItems) {
        super(id, name, activityClass);
        this.menuItems = menuItems;
    }

    public static MenuDef create(String name) {
        MenuDef menuGroup = new MenuDef();
        menuGroup.setName(name);
        return menuGroup;
    }

    public List<MenuItemDef> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDef> menuItems) {
        this.menuItems = menuItems;
    }

    public MenuDef add(MenuItemDef menuItem) {
        menuItems.add(menuItem);
        return this;
    }
}

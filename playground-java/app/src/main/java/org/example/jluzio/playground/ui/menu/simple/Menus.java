package org.example.jluzio.playground.ui.menu.simple;

import android.view.Menu;
import android.view.SubMenu;

import com.google.common.base.Objects;

public class Menus {

    public static void addToMenu(Menu menu, MenuItemDef menuItemDef) {
        boolean isRoot = menuItemDef.getId().intValue() == 0;
        if (menuItemDef instanceof MenuDef) {
            MenuDef menuDef = (MenuDef) menuItemDef;
            Menu targetMenu = menu;
            if (!isRoot) {
                SubMenu subMenu = menu.addSubMenu(Menu.NONE, menuItemDef.getId(), Menu.NONE, menuItemDef.getName());
                targetMenu = subMenu;
            }
            for (MenuItemDef childMenuItemDef : menuDef.getMenuItems()) {
                addToMenu(targetMenu, childMenuItemDef);
            }
        } else {
            if (!isRoot) {
                menu.add(Menu.NONE, menuItemDef.getId(), Menu.NONE, menuItemDef.getName());
            }
        }
    }

    public static void assignMissingIds(MenuItemDef menuItemDef, Sequence idSequence) {
        if (menuItemDef.getId() == null) {
            menuItemDef.setId(idSequence.nextValue());
        }
        if (menuItemDef instanceof MenuDef) {
            MenuDef menuDef = (MenuDef) menuItemDef;
            for (MenuItemDef childMenuItemDef : menuDef.getMenuItems()) {
                assignMissingIds(childMenuItemDef, idSequence);
            }
        }
    }

    public static MenuItemDef findMenuItem(MenuItemDef menuItemDef, int id) {
        if (Objects.equal(menuItemDef.getId(), id)) {
            return menuItemDef;
        }
        if (menuItemDef instanceof MenuDef) {
            MenuDef menuDef = (MenuDef) menuItemDef;
            for (MenuItemDef childMenuItemDef : menuDef.getMenuItems()) {
                MenuItemDef findMenuItemDef = findMenuItem(childMenuItemDef, id);
                if (findMenuItemDef != null) {
                    return findMenuItemDef;
                }
            }
        }
        return null;
    }

}
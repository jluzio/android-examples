package org.example.jluzio.playground.ui.menu.simple;

public class MenuItemDef {
    private Integer id;
    private String name;
    private Class<?> activityClass;

    public MenuItemDef() {
    }

    public MenuItemDef(Integer id, String name, Class<?> activityClass) {
        this.id = id;
        this.name = name;
        this.activityClass = activityClass;
    }

    public static MenuItemDef create(Class<?> activityClass) {
        return new MenuItemDef(null, activityClass.getSimpleName(), activityClass);
    }

    public static MenuItemDef create(String name, Class<?> activityClass) {
        return new MenuItemDef(null, name, activityClass);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
    }
}

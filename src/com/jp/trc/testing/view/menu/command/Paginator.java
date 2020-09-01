package com.jp.trc.testing.view.menu.command;

import com.jp.trc.testing.view.menu.SubMenu;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 24.08.2020 9:08
 */
public class Paginator implements Command {

    /**
     * List submenu items.
     */
    private SubMenu subMenu;

    /**
     * Menu item.
     */
    private String key;

    /**
     * Constructor for creating a paginator.
     * @param subMenu List submenu items.
     * @param key Menu item.
     */
    public Paginator(SubMenu subMenu, String key) {
        this.subMenu = subMenu;
        this.key = key;
    }

    /**
     * Executes the command entered from the keyboard.
     */
    @Override
    public void execute() {
        int currentPage = Integer.parseInt(key.replaceAll("p", ""));
        subMenu.setCurrentPage(currentPage);
        subMenu.show(currentPage);
    }
}

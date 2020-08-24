package com.jp.trc.testing.view.menu.command;

import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.SubMenu;

/**
 * Executes a menu item command.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 24.08.2020 9:06
 */
public class Executer implements Command {

    /**
     * List submenu items.
     */
    private SubMenu subMenu;

    /**
     * Menu item.
     */
    private String key;

    /**
     * Current page.
     */
    private int currentPage;

    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Constructor for creating a executer.
     * @param user Authorized user for whom the menu is formed.
     * @param subMenu List submenu items.
     * @param key Menu item.
     * @param currentPage Current page.
     */
    public Executer(User user, SubMenu subMenu, String key, int currentPage) {
        this.user = user;
        this.subMenu = subMenu;
        this.key = key;
        this.currentPage = currentPage;
    }

    /**
     * Executes the command entered from the keyboard.
     */
    @Override
    public void execute() {
        subMenu.getAction().get(Integer.parseInt(key)).execute(user, currentPage);
    }
}

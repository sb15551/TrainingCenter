package com.jp.trc.testing.view.menu.command;

import com.jp.trc.testing.view.menu.SubMenu;

import java.util.Comparator;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 24.08.2020 9:13
 */
public class Order implements Command {

    /**
     * List submenu items.
     */
    private SubMenu subMenu;

    /**
     * Menu item.
     */
    private String key;

    /**
     * Constructor for creating a order.
     * @param subMenu List submenu items.
     * @param key Menu item.
     */
    public Order(SubMenu subMenu, String key) {
        this.subMenu = subMenu;
        this.key = key;
    }

    /**
     * Executes the command entered from the keyboard.
     */
    @Override
    public void execute() {
        if (key.matches("\\s*\\+\\s*")) {
            subMenu.setComparator(Comparator.naturalOrder());
            subMenu.show(1);
        }
        if (key.matches("\\s*\\-\\s*")) {
            subMenu.setComparator(Comparator.reverseOrder());
            subMenu.show(1);
        }
    }
}

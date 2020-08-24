package com.jp.trc.testing.view.menu.command;

import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.UserAction;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.Comparator;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 24.08.2020 9:10
 */
public class Searcher implements Command {

    /**
     * List submenu items.
     */
    private SubMenu subMenu;

    /**
     * Menu item.
     */
    private String key;

    /**
     * From which menu item the submenu was formed.
     */
    private UserAction fromItemMenu;

    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Comparator for sorting.
     */
    private Comparator comparator;

    /**
     * Constructor for creating a searcher.
     * @param subMenu List submenu items.
     * @param key Menu item.
     * @param fromItemMenu From which menu item the submenu was formed.
     * @param user Authorized user for whom the menu is formed.
     * @param comparator Comparator for sorting.
     */
    public Searcher(SubMenu subMenu, String key,
                    UserAction fromItemMenu, User user, Comparator comparator) {
        this.subMenu = subMenu;
        this.key = key;
        this.fromItemMenu = fromItemMenu;
        this.user = user;
        this.comparator = comparator;
    }

    /**
     * Executes the command entered from the keyboard.
     */
    @Override
    public void execute() {
        subMenu.setSearchPhrase(key.replaceFirst("s\\s+", ""));
        subMenu.setSubMenuItems(subMenu.search(fromItemMenu, user, 1, comparator));
        int countElements = subMenu.search(fromItemMenu, user, 0, comparator).size();
        subMenu.setAmountSubmenuPages(
                countElements % SubMenu.AMOUNT_ELEMENTS_ON_PAGE == 0
                ? countElements / SubMenu.AMOUNT_ELEMENTS_ON_PAGE
                : (countElements / SubMenu.AMOUNT_ELEMENTS_ON_PAGE) + 1
        );
        subMenu.show(1);
    }
}

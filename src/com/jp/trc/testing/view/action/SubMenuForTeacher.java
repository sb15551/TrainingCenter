package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Forms a submenu taking into account search, pagination and sorting.
 * For teachers.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 21.08.2020 11:22
 */
public interface SubMenuForTeacher {

    /**
     * Get amount submenu pages.
     * @param user User for which the submenu is being created.
     * @param amountElementsOnPage Amount elements on page.
     * @return amount submenu pages.
     */
    public default int getAmountSubmenuPages(User user, int amountElementsOnPage) {
        UserController controller = new UserController();
        int amountElements = controller
                .getAllStudents(0, amountElementsOnPage, Comparator.naturalOrder())
                .size();
        return (amountElements % amountElementsOnPage) == 0
                ? amountElements / amountElementsOnPage
                : (amountElements / amountElementsOnPage) + 1;
    }

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @param page Page number to display.
     * @param amountElementsOnPage Amount elements on page.
     * @param comparator Comparator for sorting.
     * @return List<ItemMenu> Submenu.
     */
    public default List<ItemMenu> createSubMenu(User user, long page,
                                                int amountElementsOnPage, Comparator... comparator) {
        UserController controller = new UserController();
        List<Test> tmp = new ArrayList(
                controller.getAllUsers(
                        page,
                        amountElementsOnPage,
                        comparator.length == 0 ? Comparator.naturalOrder() : comparator[0]
                )
        );
        return createItemMenu(
                tmp,
                user
        );
    }

    /**
     * Search by specified parameters and create submenu.
     * @param user User for which the submenu is being created.
     * @param phrase Search phrase.
     * @param page Page number to display.
     * @param amountElementsOnPage Amount elements on page.
     * @param comparator Comparator for sorting.
     * @return List<ItemMenu> Ready submenu.
     */
    public default List<ItemMenu> search(User user, String phrase, long page,
                                         int amountElementsOnPage, Comparator... comparator) {
        UserController controller = new UserController();
        return createItemMenu(
                controller.searchUser(
                        phrase,
                        page,
                        amountElementsOnPage,
                        comparator.length == 0 ? Comparator.naturalOrder() : comparator[0]
                ),
                user
        );
    }

    /**
     * Create item menu.
     * @param list List to create a submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu> Submenu.
     */
    public List<ItemMenu> createItemMenu(List list, User user);
}

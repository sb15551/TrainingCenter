package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.Filter;
import com.jp.trc.testing.view.menu.ItemMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Forms a submenu taking into account search, pagination and sorting.
 * For students.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 21.08.2020 8:49
 */
public interface SubMenuForStudents {

    /**
     * Get amount submenu pages.
     * @param user User for which the submenu is being created.
     * @param amountElementsOnPage Amount elements on page.
     * @return amount submenu pages.
     */
    public default int getAmountSubmenuPages(User user, int amountElementsOnPage) {
        TestController controller = new TestController();
        int amountElements = controller.getAmountTestsForStudent(user.getId());
        return (amountElements % amountElementsOnPage) == 0
                ? amountElements / amountElementsOnPage
                : (amountElements / amountElementsOnPage) + 1;
    }

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @param filter Filter for paging, search and ordering.
     * @return List<ItemMenu> Submenu.
     */
    public default List<ItemMenu> createSubMenu(User user, Filter filter) {
        TestController testController = new TestController();
        List<Test> tmp = new ArrayList(
                testController.getTestsForStudent(user.getId(), filter)
        );
        return createItemMenu(tmp, user);
    }

    /**
     * Search by specified parameters and create submenu.
     * @param user User for which the submenu is being created.
     * @param filter Filter for paging, search and ordering.
     * @return List<ItemMenu> Ready submenu.
     */
    public default List<ItemMenu> search(User user, Filter filter) {
        TestController testController = new TestController();
        List<Test> tmp = new ArrayList(
                testController.searchTest(user, filter)
        );
        return createItemMenu(tmp, user);
    }

    /**
     * Create item menu.
     * @param list List to create a submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu> Submenu.
     */
    public List<ItemMenu> createItemMenu(List list, User user);
}

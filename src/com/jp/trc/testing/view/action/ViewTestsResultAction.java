package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Test results.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:34
 */
public class ViewTestsResultAction implements UserAction, SubMenuForTeacher {

    /**
     * Test id for passing.
     */
    private int testId;

    /**
     * Submenu with tests.
     */
    private SubMenu subMenu;

    /**
     * Default constructor.
     * Constructor for creating a object.
     */
    public ViewTestsResultAction() {

    }

    /**
     * Constructor for creating a object.
     * @param testId Test ID whose result need to get
     */
    public ViewTestsResultAction(int testId) {
        this.testId = testId;
    }

    /**
     * Test results.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user, int page) {
        subMenu = new SubMenu(
                user,
                "ИНФОРМАЦИЯ О РЕЗУЛЬТАТАХ СВОИХ ТЕСТОВ",
                this,
                createSubMenu(user, 1, SubMenu.AMOUNT_ELEMENTS_ON_PAGE)
        );
        if (testId == 0) {
            subMenu.show(page);
        } else {
            UserController userController = new UserController();
            TestController testController = new TestController();
            List<Assignment> assignments = testController.getResultTests(testId);
            System.out.printf(
                    "\t\t\t%-28s\t|\t%-20s\t|\t%s\n",
                    "Full name of student",
                    "Test name",
                    "Test result"
            );
            assignments.forEach(s -> System.out.printf("%-40s\t|\t%-20s\t|\t%s\n",
                    userController.getUser(s.getStudentId()).getName(),
                    testController.getTest(s.getTestId()).getTitle(),
                    s.getResult()));

            subMenu.show(page);
        }
    }

    /**
     * Get amount submenu pages.
     *
     * @param user                 User for which the submenu is being created.
     * @param amountElementsOnPage Amount elements on page.
     * @return amount submenu pages.
     */
    @Override
    public int getAmountSubmenuPages(User user, int amountElementsOnPage) {
        TestController controller = new TestController();
        int amountElements = (int) controller
                .getTestsByTeacher(user.getId(), 0, amountElementsOnPage, Comparator.naturalOrder())
                .size();
        return (amountElements % amountElementsOnPage) == 0
                ? amountElements / amountElementsOnPage
                : (amountElements / amountElementsOnPage) + 1;
    }

    /**
     * Creating submenu.
     *
     * @param user                 User for which the submenu is being created.
     * @param page                 Page number to display.
     * @param amountElementsOnPage Amount elements on page.
     * @param comparator           Comparator for sorting.
     * @return List<ItemMenu> Submenu.
     */
    @Override
    public List<ItemMenu> createSubMenu(User user, long page,
                                        int amountElementsOnPage, Comparator... comparator) {
        TestController controller = new TestController();
        List<Test> tmp = new ArrayList<>(
                controller.getTestsByTeacher(
                        user.getId(),
                        page,
                        amountElementsOnPage,
                        comparator.length == 0 ? Comparator.naturalOrder() : comparator[0])
        );
        return createItemMenu(
                tmp,
                user
        );
    }

    /**
     * Search by specified parameters and create submenu.
     *
     * @param user                 User for which the submenu is being created.
     * @param phrase               Search phrase.
     * @param page                 Page number to display.
     * @param amountElementsOnPage Amount elements on page.
     * @param comparator           Comparator for sorting.
     * @return List<ItemMenu> Ready submenu.
     */
    @Override
    public List<ItemMenu> search(User user, String phrase, long page,
                                 int amountElementsOnPage, Comparator... comparator) {
        TestController controller = new TestController();
        List<Test> tmp = new ArrayList<>(
                controller.searchTest(user, phrase, page, amountElementsOnPage, comparator.length == 0 ? Comparator.naturalOrder() : comparator[0])
        );
        Collections.sort(tmp, comparator.length == 0 ? Comparator.naturalOrder() : comparator[0]);
        return createItemMenu(
                tmp,
                user
        );
    }

    /**
     * Create item menu.
     *
     * @param list List to create a submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu> Submenu.
     */
    @Override
    public List<ItemMenu> createItemMenu(List list, User user) {
        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Test test : (List<Test>) list) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewTestsResultAction(test.getId())
            ));
        }
        return submenuItems;
    }
}

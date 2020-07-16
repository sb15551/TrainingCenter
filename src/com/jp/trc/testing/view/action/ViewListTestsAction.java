package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * List of tests available to the user.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:32
 */
public class ViewListTestsAction implements UserAction {

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
    public ViewListTestsAction() {

    }

    /**
     * Constructor for creating a object.
     * @param testId Test id.
     */
    public ViewListTestsAction(int testId) {
        this.testId = testId;
    }

    /**
     * List of tests available to the user.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        if (testId == 0) {
            subMenu = new SubMenu(user, "ИНФОРМАЦИЯ О ТЕСТАХ", createSubMenu(user));
            subMenu.show();
        } else {
            TestController controller = new TestController();
            Test test = controller.getTest(testId);
            System.out.printf(
                    "Тест \"%s\" \n"
                    + "Quentity questions: %s\n"
                    + "Author: %s\n"
                    + "Max score for passing the test: %s\n",
                    test.getTitle(),
                    controller.getQuestionQuentityInTest(test.getId()),
                    test.getAuthor().getName(),
                    test.getPassingScore()
            );
            subMenu = new SubMenu(user, "ИНФОРМАЦИЯ О ТЕСТАХ", createSubMenu(user));
            subMenu.show();
        }
    }

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenu(User user) {
        TestController controller = new TestController();
        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Test test : controller.getTestsForStudent(user.getId())) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewListTestsAction(test.getId())
            ));
        }
        return submenuItems;
    }
}

package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Test results.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:34
 */
public class ViewTestsResultAction implements UserAction {

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
    public void execute(User user) {
        if (testId == 0) {
            subMenu = new SubMenu(user, "ИНФОРМАЦИЯ О РЕЗУЛЬТАТАХ СВОИХ ТЕСТОВ", createSubMenu(user));
            subMenu.show();
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

            subMenu = new SubMenu(user, "ИНФОРМАЦИЯ О РЕЗУЛЬТАТАХ СВОИХ ТЕСТОВ", createSubMenu(user));
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
        List<Test> tests = controller.getTestsByTeacher(user.getId());

        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Test test : tests) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewTestsResultAction(test.getId())
            ));
        }
        return submenuItems;
    }
}

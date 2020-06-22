package com.jp.trc.testing.view.action;

import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.SubMenu;
import com.jp.trc.testing.view.passagetest.PassageTest;

/**
 * Displays a menu with tests or starts a test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 21:09
 */
public class SelectTestAction implements UserAction {

    /**
     * Test id for passing.
     */
    private int testId;

    /**
     * Submenu with tests.
     */
    private SubMenu subMenu;

    /**
     * Constructor for creating a object.
     * Called to form a submenu.
     */
    public SelectTestAction(User user) {
        subMenu = new SubMenu(user, this.getClass().getSimpleName());
    }

    /**
     * Constructor for creating a object.
     * Called to pass the test.
     */
    public SelectTestAction(int testId) {
        this.testId = testId;
    }

    /**
     * If the test is not selected, it displays a menu with tests for passing.
     * If a test is selected, then the test begins.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        if (testId == 0) {
            subMenu.show();
        } else {
            new PassageTest(user, testId).beginTest();
        }
    }
}

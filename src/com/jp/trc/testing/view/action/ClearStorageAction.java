package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.User;

/**
 * Clear storage, leaved 1 administrator entry for further filling.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 17.09.2020 14:14
 */
public class ClearStorageAction implements UserAction {

    private UserController userController = new UserController();
    private TestController testController = new TestController();

    /**
     * Action when selecting a menu item.
     *
     * @param user The user of this institution for whom the action is performed.
     * @param page Page number to display.
     */
    @Override
    public void execute(User user, int page) {
        userController.deleteAllData();
    }
}

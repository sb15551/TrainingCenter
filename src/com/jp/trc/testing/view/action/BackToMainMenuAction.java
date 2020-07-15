package com.jp.trc.testing.view.action;

import com.jp.trc.testing.model.users.User;

/**
 * Back to the main menu.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:30
 */
public class BackToMainMenuAction implements UserAction {

    /**
     * Back to the main menu.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        return;
    }
}

package com.jp.trc.testing.view.action;

import com.jp.trc.testing.model.users.User;

/**
 * Exit program.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class ExitProgram implements UserAction {

    /**
     * Exit program.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        System.exit(0);
    }
}

package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.Institute;
import com.jp.trc.testing.model.users.User;

/**
 * Interface for creating an action.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 9:28
 */
public interface UserAction {

    /**
     * Action when selecting a menu item.
     * @param center Institution in which the action is performed.
     * @param user The user of this institution for whom the action is performed.
     */
    void execute(Institute center, User user);

}

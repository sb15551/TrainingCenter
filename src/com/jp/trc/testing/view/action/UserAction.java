package com.jp.trc.testing.view.action;

import com.jp.trc.testing.model.users.User;

/**
 * Interface for creating an action.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 9:28
 */
public interface UserAction {

    /**
     * Action when selecting a menu item.
     * @param user The user of this institution for whom the action is performed.
     * @param page Page number to display.
     */
    void execute(User user, int page);

}

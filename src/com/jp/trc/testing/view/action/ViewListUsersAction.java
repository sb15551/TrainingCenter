package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Lists of all users of this institution.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:35
 */
public class ViewListUsersAction implements UserAction {

    /**
     * Lists of all users of this institution.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        List<User> users = new UserController().getAllUsers();
        Collections.sort(users, Comparator.naturalOrder());
        System.out.printf(
                "\t\t\t%-28s\t|\t%-10s\t|\t%-10s\t|\t%s\t|\t%s\n",
                "Full name user",
                "Login",
                "Password",
                "Age",
                "Type"
        );
        users.forEach(System.out::println);
    }
}

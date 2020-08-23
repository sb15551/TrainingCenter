package com.jp.trc.testing.view.action;

import com.jp.trc.testing.model.users.User;

/**
 * Displays information about the user.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.07.2020 14:23
 */
public class ViewUserInfoAction implements UserAction {

    private User usr;

    /**
     * Constructor for creating a object.
     * @param usr
     */
    public ViewUserInfoAction(User usr) {
        this.usr = usr;
    }

    @Override
    public void execute(User user, int page) {
        System.out.printf(
                "Full name user: %s\n"
                + "Login: %s\n"
                + "Password: %s\n"
                + "Age: %s\n"
                + "Type: %s",
                usr.getName(), usr.getLogin(), usr.getPassword(), usr.getAge(),
                usr.getClass().getSimpleName()
        );
    }
}

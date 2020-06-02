package com.jp.trc.testing.model.users;

/**
 * User type "Admin".
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Admin extends User {

    /**
     * Constructor for creating a user.
     * @param id User id.
     * @param name Full name user.
     * @param login Login user.
     * @param password Password user.
     * @param age Age user.
     */
    public Admin(int id, String name, String login, String password, int age) {
        super(id, name, login, password, age);
    }
}

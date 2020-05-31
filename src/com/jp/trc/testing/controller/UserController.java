package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for working with users of the institution.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 27.05.2020 8:40
 */
public class UserController {

    /**
     * List of all users.
     * @return List of all users.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(Repository.getUsers().values());
    }

    /**
     * Does such a login exist in the database.
     * @param login Login to check.
     * @return true if login is finded.
     */
    public boolean existsLogin(String login) {
        return Repository.getUsers().containsKey(login);
    }

    /**
     * Verifies user password.
     * @param login User login for which password is to be verified.
     * @param password Password to check.
     * @return true if the password matches.
     */
    public boolean verifiesPassword(String login, String password) {
        return Repository.getUsers().get(login).getPassword().equals(password);
    }
}

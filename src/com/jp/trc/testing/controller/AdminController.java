package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for users of type "Admin".
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 9:28
 */
public class AdminController {

    public static class ViewListUsers implements UserAction {

        /**
         * Lists of all users of this institution.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(TrainingCenter center, User user) {
            List<User> temp = new ArrayList<>(center.getUsers().values());
            Collections.sort(temp, Comparator.naturalOrder());
            System.out.printf("\t\t\t%-28s\t|\t%-10s\t|\t%-10s\t|\t%s\t|\t%s\n", "Full name user", "Login", "Password", "Age", "Type");
            temp.forEach(System.out::println);
        }
    }
}

package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.Input;

/**
 * Controller for users of type "Student".
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 9:28
 */
public class StudentController {

    public static class ViewListTests implements UserAction {

        /**
         * List of tests available to the user.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(TrainingCenter center, User user) {
            center.getTests().forEach(s -> System.out.printf("Тест \"%s\" - Number questions: %s\n", s.getTitle(), s.getQuestions().size()));
        }
    }

}

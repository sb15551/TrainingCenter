package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.Input;

public class StudentController {

    public static class ViewListTests implements UserAction {

        @Override
        public void execute(Input input, TrainingCenter center, User user) {
            center.getTests().forEach(s -> System.out.printf("Тест \"%s\" - Количество вопросов: %s\n", s.getTitle(), s.getQuestions().size()));
        }
    }

}

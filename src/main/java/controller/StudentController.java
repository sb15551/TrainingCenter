package controller;

import model.TrainingCenter;
import model.users.User;
import view.Input;

public class StudentController {

    public static class ViewListTests implements UserAction {

        @Override
        public void execute(Input input, TrainingCenter center, User user) {
            center.getTests().forEach(s -> System.out.printf("Тест \"%s\" - Количество вопросов: %s\n", s.getTitle(), s.getQuestions().size()));
        }
    }

}

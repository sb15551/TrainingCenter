package controller;

import model.TrainingCenter;
import model.users.User;
import view.Input;

public class ExitProgram implements UserAction {
    @Override
    public void execute(Input input, TrainingCenter center, User user) {
        System.exit(0);
    }
}

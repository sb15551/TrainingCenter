package controller;

import model.TrainingCenter;
import model.users.User;
import view.Input;

public interface UserAction {

    void execute(Input input, TrainingCenter center, User user);

}

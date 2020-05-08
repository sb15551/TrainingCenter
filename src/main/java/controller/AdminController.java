package controller;

import model.TrainingCenter;
import model.users.User;
import view.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdminController {

    public static class ViewListUsers implements UserAction {
        @Override
        public void execute(Input input, TrainingCenter center, User user) {
            List<User> temp = new ArrayList<>(center.getUsers().values());
            Collections.sort(temp, Comparator.naturalOrder());
            System.out.printf("\t\t\t%-28s\t|\t%-10s\t|\t%-10s\t|\t%s\t|\t%s\n", "ФИО", "Login", "Password", "Age", "Type");
            temp.forEach(System.out::println);
        }
    }
}

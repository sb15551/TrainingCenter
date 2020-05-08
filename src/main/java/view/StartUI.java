package view;

import model.TrainingCenter;
import model.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StartUI {
    private final Input input;

    private final TrainingCenter center;

    private final Consumer<String> output;

    public StartUI(Input input, TrainingCenter center, Consumer<String> output) {
        this.input = input;
        this.center = center;
        this.output = output;
    }

    public void init() {
        LoginForm loginForm = new LoginForm(center);
        User loginUser = loginForm.login();
        String typeUser = loginUser.getClass().getSimpleName();
        Menu menu = new Menu(loginUser, this.center, this.input, this.output);
        List<Integer> range = new ArrayList<>();
        menu.show(typeUser);
        for (int i = 0; i < menu.getActionsLength(); i++) {
            range.add(i);
        }
        while (true) {
            menu.select(input.ask("Введите пункт меню: ", range));
            menu.show(typeUser);
        }
    }

//    public static void main(String[] args) {
//        new StartUI(new ValidateInput(new ConsoleInput()), new TrainingCenter(), System.out::println).init();
//    }
}

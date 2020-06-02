package com.jp.trc.testing.view;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 * Creates user interface.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class UILauncher {

    /**
     * Command input.
     */
    private final Input input;

    /**
     * Constructor for creating an object.
     * @param input Keyboard input.
     */
    public UILauncher(Input input) {
        this.input = input;
    }

    /**
     * User interface initialization.
     */
    public void init() {
        LoginForm loginForm = new LoginForm();
        User loginUser = loginForm.login();
        String typeUser = loginUser.getClass().getSimpleName();
        Menu menu = new Menu(loginUser);
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
//        new StartUI(new ValidateInput(new ConsoleInput()), new TrainingCenter()).init();
//    }
}

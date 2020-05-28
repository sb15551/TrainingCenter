package com.jp.trc.testing.view;

import com.jp.trc.testing.model.Institute;
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
     * Institution for which the user interface is created.
     */
    private final Institute center;

    public UILauncher(Input input, Institute center) {
        this.input = input;
        this.center = center;
    }

    /**
     * User interface initialization.
     */
    public void init() {
        LoginForm loginForm = new LoginForm(center);
        User loginUser = loginForm.login();
        String typeUser = loginUser.getClass().getSimpleName();
        Menu menu = new Menu(loginUser, this.center);
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

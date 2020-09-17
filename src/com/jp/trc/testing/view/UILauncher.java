package com.jp.trc.testing.view;

import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.Input;
import com.jp.trc.testing.view.input.InputValidator;
import com.jp.trc.testing.view.menu.Menu;

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
        Menu menu = new Menu(loginUser);
        List<Integer> range = new ArrayList<>();
        menu.show();
        for (int i = 0; i < menu.getActionsLength(); i++) {
            range.add(i);
        }
        while (true) {
            menu.select(input.ask("Введите пункт меню: ", range));
            menu.show();
        }
    }

    public static void main(String[] args) {
        new UILauncher(new InputValidator(new ConsoleInput())).init();
    }
}

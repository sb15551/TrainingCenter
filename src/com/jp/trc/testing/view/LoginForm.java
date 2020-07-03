package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.UserAction;
import com.jp.trc.testing.view.exception.LoginExistsException;
import com.jp.trc.testing.view.exception.VerifiesPasswordException;

import java.util.Scanner;

/**
 * Authorization form.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class LoginForm {

    private Scanner scanner;
    private UserController controller;

    /**
     * Authorizes the user.
     * @return User who is logged in.
     */
    public User login() {
         scanner = new Scanner(System.in);
        controller = new UserController();

        System.out.print("Введите логин: ");
        String login = enterLogin();
        System.out.print("Введите пароль: ");
        String password = enterPassword(login);

        User user = controller.getUser(login);
        System.out.printf(
                "\n\nДобро пожаловать, %s\n\n",
                user.getName()
        );
        return user;
    }

    private String enterLogin() {
        String login = scanner.nextLine();
        try {
            controller.existsLogin(login);
        } catch (LoginExistsException lee) {
            System.out.print(lee.getMessage());
            login = enterLogin();
        }
        return login;
    }

    private String enterPassword(String login) {
        String password = scanner.nextLine();
        try {
            controller.verifiesPassword(login, password);
        } catch (VerifiesPasswordException vpe) {
            System.out.print(vpe.getMessage());
            password = enterPassword(login);
        }
        return password;
    }
}

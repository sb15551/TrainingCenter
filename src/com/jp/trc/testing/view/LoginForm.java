package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.User;

import java.util.Scanner;

/**
 * Authorization form.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class LoginForm {

    /**
     * Authorizes the user.
     * @return User who is logged in.
     */
    public User login() {
        Scanner scanner = new Scanner(System.in);
        UserController controller = new UserController();
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        while (!controller.existsLogin(login)) {
            System.out.println("Такого логина не существует!");
            System.out.print("Введите заново: ");
            login = scanner.nextLine();
        }
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        while (!controller.verifiesPassword(login, password) ) {
            System.out.println("Пароль не верный!!!");
            System.out.print("Введите еще раз: ");
            password = scanner.nextLine();
        }
        System.out.printf("\n\nДобро пожаловать, %s\n\n", Repository.getUsers().get(login).getName());
        return Repository.getUsers().get(login);
    }
}

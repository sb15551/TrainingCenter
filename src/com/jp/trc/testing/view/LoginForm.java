package com.jp.trc.testing.view;

import com.jp.trc.testing.model.Institute;
import com.jp.trc.testing.model.users.User;

import java.util.Scanner;

/**
 * Authorization form.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class LoginForm {

    /**
     * Institution where authorization is performed.
     */
    private Institute center;

    public LoginForm(Institute center) {
        this.center = center;
    }

    /**
     * Authorizes the user.
     * @return User who is logged in.
     */
    public User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        while (!center.getUsers().containsKey(login)) {
            System.out.println("Такого логина не существует!");
            System.out.print("Введите заново: ");
            login = scanner.nextLine();
        }
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        while (!center.getUsers().get(login).getPassword().equals(password)) {
            System.out.println("Пароль не верный!!!");
            System.out.print("Введите еще раз: ");
            password = scanner.nextLine();
        }
        System.out.printf("\n\nДобро пожаловать, %s\n\n", center.getUsers().get(login).getName());
        return center.getUsers().get(login);
    }
}

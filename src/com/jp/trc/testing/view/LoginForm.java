package com.jp.trc.testing.view;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;

import java.util.Scanner;

public class LoginForm {
    private TrainingCenter center;

    public LoginForm(TrainingCenter center) {
        this.center = center;
    }

    public User login() {
        boolean result = false;
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

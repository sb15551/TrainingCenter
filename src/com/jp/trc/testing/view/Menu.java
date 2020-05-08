package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.*;
import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Menu {
    private List<ItemMenu> menuItems;
    private List<UserAction> action = new ArrayList<>();

    private User user;
    private TrainingCenter center;
    private Input input;
    private final Consumer<String> output;

    public Menu(User user, TrainingCenter center, Input input, Consumer<String> output) {
        this.user = user;
        this.center = center;
        this.input = input;
        this.output = output;
        createMenu();
    }

    public void show(String type) {
        List<ItemMenu> menu = new ArrayList<ItemMenu>();
        int key = 0;
        for (ItemMenu item : menuItems) {
            if (item.getType().equals(type)) {
                action.add(key, item.getAction());
                item.setKey(key++);
                menu.add(item);
            }
        }
        menu.add(new ItemMenu(key,"Выход", type, new ExitProgram()));
        action.add(key, menu.get(menu.size() - 1).getAction());
        menu.forEach(System.out::println);
    }

    private void createMenu() {
        menuItems = new ArrayList();
        menuItems.add(new ItemMenu("Посмотреть список тестов", "Student", new StudentController.ViewListTests()));

        menuItems.add(new ItemMenu("Посмотреть свои тесты", "Teacher", new TeacherController.ViewYourTests()));
        menuItems.add(new ItemMenu("Посмотреть результаты своих тестов", "Teacher", new TeacherController.ViewTestsResult()));

        menuItems.add(new ItemMenu("Посмотреть список пользователей", "Admin", new AdminController.ViewListUsers()));
    }

    public int getActionsLength() {
        return this.action.size();
    }

    public void select(int key) {
        System.out.println();
        this.action.get(key).execute(input, center, user);
        System.out.println();
    }

}

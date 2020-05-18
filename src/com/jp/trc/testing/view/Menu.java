package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.*;
import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Creating menu items.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Menu {

    /**
     * List all menu items.
     */
    private List<ItemMenu> menuItems;

    /**
     * List actions for each menu item.
     */
    private List<UserAction> action = new ArrayList<>();


    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Institution for which the menu is made.
     */
    private TrainingCenter center;

    /**
     * Constructor for creating a menu
     * @param user Authorized user for whom the menu is formed.
     * @param center Institution for which the menu is made.
     */
    public Menu(User user, TrainingCenter center) {
        this.user = user;
        this.center = center;
        createMenu();
    }

    /**
     * Creating menus for a specific type of user.
     * @param type Type of user who is logged in.
     */
    public void show(String type) {
        List<ItemMenu> menu = new ArrayList<ItemMenu>();
        int key = 0;
        for (ItemMenu item : menuItems) {
            if (item.getTypeUser().equals(type)) {
                action.add(key, item.getAction());
                item.setKey(key++);
                menu.add(item);
            }
        }
        menu.add(new ItemMenu(key,"Exit", type, new ExitProgram()));
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

    /**
     * @return Number of actions for the created menu.
     */
    public int getActionsLength() {
        return this.action.size();
    }

    /**
     * Executes action of a menu item with a key.
     * @param key Menu item.
     */
    public void select(int key) {
        System.out.println();
        this.action.get(key).execute(center, user);
        System.out.println();
    }

}

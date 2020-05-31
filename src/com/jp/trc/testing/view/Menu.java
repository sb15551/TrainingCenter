package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.*;
import com.jp.trc.testing.model.Repository;
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
    private List<ItemMenu> menuItems = new ArrayList<>();

    /**
     * List actions for each menu item.
     */
    private List<UserAction> action = new ArrayList<>();


    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Constructor for creating a menu
     * @param user Authorized user for whom the menu is formed.
     */
    public Menu(User user) {
        this.user = user;
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
        menu.add(new ItemMenu(key, "Exit", type, new ExitProgram()));
        action.add(key, menu.get(menu.size() - 1).getAction());
        menu.forEach(System.out::println);
    }

    private void createMenu() {
        menuItems.add(new ItemMenu("Посмотреть список тестов", "Student", new MenuAction.ViewListTestsAction()));

        menuItems.add(new ItemMenu("Посмотреть свои тесты", "Teacher", new MenuAction.ViewYourTestsAction()));
        menuItems.add(new ItemMenu("Посмотреть результаты своих тестов", "Teacher", new MenuAction.ViewTestsResultAction()));

        menuItems.add(new ItemMenu("Посмотреть список пользователей", "Admin", new MenuAction.ViewListUsersAction()));
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
        this.action.get(key).execute(user);
        System.out.println();
    }

}

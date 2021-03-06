package com.jp.trc.testing.view.menu;

import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.*;

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
    private List<UserAction> action;

    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Constructor for creating a menu.
     * @param user Authorized user for whom the menu is formed.
     */
    public Menu(User user) {
        this.user = user;
        createMenu();
    }

    /**
     * Creating menus for a specific type of user.
     */
    public void show() {
        List<ItemMenu> menu = new ArrayList<ItemMenu>();
        action  = new ArrayList<>();

        menu.add(new ItemMenu(0, "Exit", user.getClass().getSimpleName(), new ExitProgram()));
        action.add(0, menu.get(menu.size() - 1).getAction());

        int key = 1;
        for (ItemMenu item : menuItems) {
            if (item.getTypeUser().equals(user.getClass().getSimpleName())) {
                action.add(key, item.getAction());
                item.setKey(key++);
                menu.add(item);
            }
        }
        System.out.println("ГЛАВНОЕ МЕНЮ");
        printMenu(menu);
    }

    private void printMenu(List<ItemMenu> menu) {
        menu.stream().filter(itemMenu -> itemMenu.getKey() != 0)
                .forEach(System.out::println);
        menu.stream().filter(itemMenu -> itemMenu.getKey() == 0)
                .forEach(System.out::println);
    }

    private void createMenu() {
        menuItems.add(new ItemMenu(
                "Посмотреть список тестов",
                "Student",
                new ViewListTestsAction()
        ));
        menuItems.add(new ItemMenu(
                "Выбрать тест для прохождения",
                "Student",
                new SelectTestAction()
        ));
        menuItems.add(new ItemMenu(
                "Статистика тестов",
                "Student",
                new ViewTestStatisticAction()
        ));

        menuItems.add(new ItemMenu(
                "Посмотреть результаты своих тестов",
                "Teacher",
                new ViewTestsResultAction()
        ));
        menuItems.add(new ItemMenu(
                "Посмотреть рейтинг студентов по группам",
                "Teacher",
                new ViewRatingByGroupAction()
        ));

        menuItems.add(new ItemMenu(
                "Посмотреть список пользователей",
                "Admin",
                new ViewListUsersAction()
        ));

        menuItems.add(new ItemMenu(
                "Экспортировать всю базу данных в файл",
                "Admin",
                new DataExportAction()
        ));

        menuItems.add(new ItemMenu(
                "Очистить базу данных",
                "Admin",
                new ClearStorageAction()
        ));
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
        this.action.get(key).execute(user, 1);
        System.out.println();
    }

    /**
     * Gets user.
     *
     * @return value of user com.jp.trc.testing.model.users.User
     */
    public User getUser() {
        return user;
    }
}

package com.jp.trc.testing.view.menu;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.*;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.Input;
import com.jp.trc.testing.view.input.InputValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 05.06.2020 7:28
 */
public class SubMenu {

    /**
     * List submenu items.
     */
    private List<ItemMenu> submenuItems = new ArrayList<>();

    /**
     * Pages and elements on these pages.
     */
    private HashMap<String, List<ItemMenu>> pagesWithSubMenuItem;

    /**
     * Keyboard input.
     */
    private Input input = new InputValidator(new ConsoleInput());

    /**
     * List actions for each submenu item.
     */
    private List<UserAction> action;

    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Submenu name.
     */
    private String subMenuName;

    /**
     * Create additional menu.
     */
    private AdditionalMenu additionalMenu;

    /**
     * Constructor for creating a submenu.
     *
     * @param user Authorized user for whom the menu is formed.
     * @param nameMenu The menu item for which you want to create a submenu.
     */
    public SubMenu(User user, String nameMenu) {
        this.user = user;
        subMenuName = createSubMenu(nameMenu);
    }

    /**
     * Displays a submenu.
     * @param page Page number to display. If the "page" array is empty, the first page is displayed.
     */
    public void show(String ... page) {
        if (page.length == 0)
            buildSubMenu();
        else
            buildSubMenu(page[0]);

        String key = input.askSubMenu(
                "Введите пункт подменю: ",
                this.action.size(),
                pagesWithSubMenuItem.size() == 1
                        ? pagesWithSubMenuItem.size()
                        : pagesWithSubMenuItem.size() - 1

        );

        select(key);
    }

    /**
     * Executes action of a menu item with a key.
     * @param key Menu item.
     */
    public void select(String key) {
        System.out.println();
        if (key.matches("\\d+")) {
            this.action.get(Integer.parseInt(key)).execute(user);
        } else if (key.matches("^p\\s*\\d+")) {
            if (pagesWithSubMenuItem.size() == 1) {
                show();
            } else {
                show(key);
            }
        }
        System.out.println();
    }

    /**
     * Building submenu and creating list actions.
     */
    private void buildSubMenu(String ... page) {
        List<ItemMenu> subMenu = new ArrayList<ItemMenu>();
        List<ItemMenu> subMenuItemsOnPage;

        if (page.length == 0) {
            subMenuItemsOnPage = pagesWithSubMenuItem.get("p1");
        } else {
            subMenuItemsOnPage = pagesWithSubMenuItem.get(page[0]);
        }

        action = new ArrayList<>();
        subMenu.add(new ItemMenu(
                0,
                "Back",
                user.getClass().getSimpleName(),
                new BackToMainMenuAction()
        ));
        action.add(subMenu.get(subMenu.size() - 1).getAction());
        int key = 1;
        for (ItemMenu item : subMenuItemsOnPage) {
            action.add(key, item.getAction());
            item.setKey(key++);
            subMenu.add(item);
        }

        printMenu(subMenu, page.length == 0 ? "p1" : page[0]);
    }

    /**
     * Displays the menu on the screen so that the exit button is at the bottom.
     * @param submenu List SubMenu.
     */
    private void printMenu(List<ItemMenu> submenu, String ... page) {
        System.out.println("\n\t" + subMenuName);
        String numberPage = "1";
        if (pagesWithSubMenuItem.size() > 1){
            numberPage = page[0].equals("p0") ? "all" : page[0].replaceAll("p", "");
            System.out.printf("--------------------- Page %s ---------------------\n", numberPage);
        }

        submenu.stream().filter(itemMenu -> itemMenu.getKey() != 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));
        submenu.stream().filter(itemMenu -> itemMenu.getKey() == 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));

        System.out.println("--------------------- Additional menu ---------------------");
        if (pagesWithSubMenuItem.size() > 1) {
            StringBuilder pages = new StringBuilder();
            for (int i = 1; i < pagesWithSubMenuItem.size(); i++) {
                pages.append(i);
                pages.append(" ");
            }
            pages.append(0);
            System.out.printf(
                    "\tp. %s (Введите \"p\" и номер нужной страницы для ее отображения. " +
                            "\"p0\" вернет весь список)\n",
                    pages.toString()
                    );
        }

        System.out.println(
                "\ts. (Чтобы выполнить поиск введите \"s \" и искомую фразу)\n"
        );
    }

    /**
     * Creates a submenu for a specific menu item.
     * @param nameMenu Menu item for which a submenu is created.
     * @return Returns the name of the submenu.
     */
    private String createSubMenu(String nameMenu) {
        if (nameMenu.equals("ViewRatingByGroupAction")) {
            createSubMenuForGroup();
            return "РЕЙТИНГ СТУДЕНТОВ ПО ГРУППАМ";
        }
        if (nameMenu.equals("SelectTestAction")) {
            createSubMenuForTests();
            return "ВЫБОР ТЕСТА ДЛЯ ПРОХОЖДЕНИЯ";
        }
        if (nameMenu.equals("ViewTestStatisticAction")) {
            createSubMenuForStatisticsTests();
            return "ВЫБОР ТЕСТА ДЛЯ ПРОСМОТРА СТАТИСТИКИ";
        }
        return null;
    }

    /**
     * Create a submenu to display test statistics.
     */
    private void createSubMenuForStatisticsTests() {
        TestController controller = new TestController();
        for (Test test : controller.getTestsForStudent(user.getId())) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewTestStatisticAction(test.getId())
            ));
        }
        pagesWithSubMenuItem = new AdditionalMenu().createPagination(submenuItems);
    }

    /**
     * Creating submenu for tests.
     */
    private void createSubMenuForTests() {
        TestController controller = new TestController();
        for (Test test : controller.getTestsForStudent(user.getId())) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new SelectTestAction(test.getId())
            ));
        }
        pagesWithSubMenuItem = new AdditionalMenu().createPagination(submenuItems);
    }

    /**
     * Creating submenu for groups.
     */
    private void createSubMenuForGroup() {
        for (Group group : Repository.getGroups()) {
            submenuItems.add(new ItemMenu(
                    group.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewRatingByGroupAction(group.getId())
            ));
        }
    }
}

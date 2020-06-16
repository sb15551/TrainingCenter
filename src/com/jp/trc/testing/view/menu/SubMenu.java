package com.jp.trc.testing.view.menu;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.BackToMainMenuAction;
import com.jp.trc.testing.view.action.UserAction;
import com.jp.trc.testing.view.action.ViewRatingByGroupAction;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.Input;
import com.jp.trc.testing.view.input.InputValidator;

import java.util.ArrayList;
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
     */
    public void show() {
        buildSubMenu();
        List<Integer> range = new ArrayList<>();
        for (int i = 0; i < this.action.size(); i++) {
            range.add(i);
        }

        while (true) {
            int number = input.ask("Введите пункт подменю: ", range);
            if (number == 0) {
                return;
            }
            select(number);
            buildSubMenu();
        }
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

    /**
     * Building submenu and creating list actions.
     */
    private void buildSubMenu() {
        List<ItemMenu> subMenu = new ArrayList<ItemMenu>();
        action = new ArrayList<>();
        subMenu.add(new ItemMenu(
                0,
                "Back",
                user.getClass().getSimpleName(),
                new BackToMainMenuAction()
        ));
        action.add(subMenu.get(subMenu.size() - 1).getAction());
        int key = 1;
        for (ItemMenu item : submenuItems) {
            action.add(key, item.getAction());
            item.setKey(key++);
            subMenu.add(item);
        }

        System.out.println("\t" + subMenuName);
        printMenu(subMenu);
    }

    /**
     * Displays the menu on the screen so that the exit button is at the bottom.
     * @param submenu List SubMenu.
     */
    private void printMenu(List<ItemMenu> submenu) {
        submenu.stream().filter(itemMenu -> itemMenu.getKey() != 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));
        submenu.stream().filter(itemMenu -> itemMenu.getKey() == 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));
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
        return null;
    }

    /**
     * Creating submenu for groups.
     */
    private void createSubMenuForGroup() {
        for (Group group : Repository.getGroups()) {
            submenuItems.add(new ItemMenu(
                    group.getTitle(),
                    "Teacher",
                    new ViewRatingByGroupAction(group.getId())
            ));
        }
    }
}

package com.jp.trc.testing.view;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 05.06.2020 7:28
 */
public class Submenu {

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
    private String submenuName;

    /**
     * Constructor for creating a submenu.
     *
     * @param user Authorized user for whom the menu is formed.
     * @param nameMenu The menu item for which you want to create a submenu.
     */
    public Submenu(User user, String nameMenu) {
        this.user = user;
        submenuName = createSubmenu(nameMenu);
    }

    /**
     * Displays a submenu.
     */
    public void show() {
        toBuild();
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
            toBuild();
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
     * To build submenu.
     */
    private void toBuild() {
        List<ItemMenu> submenu = new ArrayList<ItemMenu>();
        action = new ArrayList<>();
        submenu.add(new ItemMenu(0, "Back", user.getClass().getSimpleName(), new BackMainMenu()));
        action.add(submenu.get(submenu.size() - 1).getAction());
        int key = 1;
        for (ItemMenu item : submenuItems) {
            action.add(key, item.getAction());
            item.setKey(key++);
            submenu.add(item);
        }

        System.out.println("\t" + submenuName);
        printMenu(submenu);
    }

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
    private String createSubmenu(String nameMenu) {
        if (nameMenu.equals("RatingStudentsByGroup")) {
            createSubmenuByGroup();
            return "РЕЙТИНГ СТУДЕНТОВ ПО ГРУППАМ";
        }
        return null;
    }

    /**
     * Creating submenu for "RatingStudentsByGroup".
     */
    private void createSubmenuByGroup() {
        for (Group group : Repository.getGroups()) {
            submenuItems.add(new ItemMenu(
                    group.getTitle(),
                    "Teacher",
                    new MenuAction.RatingStudentsByGroup(group.getId())
            ));
        }
    }

    /**
     * Back to the main menu.
     */
    public static class BackMainMenu implements UserAction {

        /**
         * Back to the main menu.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            new Menu(user).show();
        }
    }
}

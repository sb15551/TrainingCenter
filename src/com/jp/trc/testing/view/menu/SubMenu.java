package com.jp.trc.testing.view.menu;

import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.*;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.Input;
import com.jp.trc.testing.view.input.InputValidator;
import com.jp.trc.testing.view.menu.command.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 05.06.2020 7:28
 */
public class SubMenu {

    /**
     * Amount elements on page.
     */
    public static final int AMOUNT_ELEMENTS_ON_PAGE = 5;

    /**
     * From which menu item the submenu was formed.
     */
    private UserAction fromItemMenu;

    /**
     * List submenu items.
     */
    private List<ItemMenu> subMenuItems;

    /**
     * Amount pages.
     */
    private int amountSubmenuPages;

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
     * Current page.
     */
    private int currentPage = 1;

    /**
     * Search phrase.
     */
    private String searchPhrase = "";

    /**
     * Comparator for sorting.
     */
    private Comparator comparator = Comparator.naturalOrder();

    /**
     * Command storage.
     */
    private HashMap<String, Command> commands = new HashMap<>();

    /**
     * Menu item.
     */
    private String key;

    /**
     * Gets action.
     *
     * @return value of action java.util.List<com.jp.trc.testing.view.action.UserAction>
     */
    public List<UserAction> getAction() {
        return action;
    }

    /**
     * Sets value searchPhrase.
     *
     * @param searchPhrase value of searchPhrase
     */
    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    /**
     * Sets value subMenuItems.
     *
     * @param subMenuItems value of subMenuItems
     */
    public void setSubMenuItems(List<ItemMenu> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    /**
     * Sets value amountSubmenuPages.
     *
     * @param amountSubmenuPages value of amountSubmenuPages
     */
    public void setAmountSubmenuPages(int amountSubmenuPages) {
        this.amountSubmenuPages = amountSubmenuPages;
    }

    /**
     * Sets value comparator.
     *
     * @param comparator value of comparator
     */
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    /**
     * Sets value currentPage.
     *
     * @param currentPage value of currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Constructor for creating a submenu.
     *
     * @param user Authorized user for whom the menu is formed.
     * @param nameMenu The menu item for which you want to create a submenu.
     */
    public SubMenu(User user, String nameMenu, UserAction typeSubMenu, List<ItemMenu> subMenuItems) {
        this.user = user;
        subMenuName = nameMenu;
        this.fromItemMenu = typeSubMenu;
        this.subMenuItems = subMenuItems;
        amountSubmenuPages = getAmountSubmenuPages(typeSubMenu, user);
    }

    /**
     * Displays a submenu.
     * @param page Page number to display. If the "page" array is empty, the first page is displayed.
     */
    public void show(int page) {
        buildSubMenu(page);

        key = input.askSubMenu(
                "Введите пункт подменю: ",
                this.action.size(),
                amountSubmenuPages
        );

        initCommands();
        select(key);
    }

    /**
     * Executes action of a menu item with a key.
     * @param key Menu item.
     */
    public void select(String key) {
        System.out.println();
        for (String pattern : commands.keySet()) {
            if (key.matches(pattern)) {
                commands.get(pattern).execute();
            }
        }
        System.out.println();
    }

    /**
     * Building submenu and creating list actions.
     */
    private void buildSubMenu(int page) {
        List<ItemMenu> subMenu = new ArrayList<ItemMenu>();
        List<ItemMenu> subMenuItemsOnPage;

        subMenuItemsOnPage = searchPhrase.equals("")
                ? getPageSubMenu(fromItemMenu, user, page, comparator)
                : search(fromItemMenu, user, page, comparator);

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

        printMenu(subMenu, page);
    }

    /**
     * Displays the menu on the screen so that the exit button is at the bottom.
     * @param submenu List SubMenu.
     */
    private void printMenu(List<ItemMenu> submenu, int page) {
        System.out.println("\n\t" + subMenuName);
        String numberPage = "1";
        if (amountSubmenuPages > 1) {
            numberPage = page == 0 ? "all" : String.valueOf(page);
            System.out.printf(
                    "--------------------- Page %s%s ---------------------\n",
                    numberPage,
                    searchPhrase.equals("") ? "" : " (Search phrase: " + searchPhrase + ")"
            );
        }

        submenu.stream().filter(itemMenu -> itemMenu.getKey() != 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));
        submenu.stream().filter(itemMenu -> itemMenu.getKey() == 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));

        if (amountSubmenuPages > 1) {
            System.out.println("--------------------- Additional menu ---------------------");
            StringBuilder pages = new StringBuilder();
            for (int i = 1; i <= amountSubmenuPages; i++) {
                pages.append(i);
                pages.append(" ");
            }
            pages.append(0);
            System.out.printf(
                    "\tp. %s (Введите \"p\" и номер нужной страницы для ее отображения. "
                            + "\"p0\" вернет весь список)\n",
                    pages.toString()
                    );

            System.out.println(
                    "\ts. (Чтобы выполнить поиск введите \"s \" и искомую фразу)\n"
                    + "\tВведите \"+\" или \"-\" чтобы осортировать в алфавитном порядке"
            );
        }
    }

    private List<ItemMenu> getPageSubMenu(UserAction clazz, User user,
                                          long page, Comparator... comparator) {
        Object object = null;
        try {
            object = clazz.getClass()
                    .getMethod("createSubMenu", User.class, long.class,
                            int.class, Comparator[].class)
                    .invoke(
                            clazz,
                            user,
                            page,
                            AMOUNT_ELEMENTS_ON_PAGE,
                            comparator.length == 0 ? Comparator.naturalOrder() : comparator
                    );
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object == null ? subMenuItems: (List<ItemMenu>) object;
    }

    private int getAmountSubmenuPages(UserAction clazz, User user) {
        Object object = null;
        try {
            object = clazz.getClass()
                    .getMethod("getAmountSubmenuPages", User.class, int.class)
                    .invoke(clazz, user, AMOUNT_ELEMENTS_ON_PAGE);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object == null ? 0: (int) object;
    }

    /**
     * Search by specified parameters and create submenu.
     * @param clazz From which menu item the submenu was formed.0
     * @param user Authorized user for whom the menu is formed.
     * @param page Page number to display.
     * @param comparator Comparator for sorting.
     * @return List<ItemMenu> Ready submenu.
     */
    public List<ItemMenu> search(UserAction clazz, User user, long page,
                                  Comparator... comparator) {
        Object object = null;
        try {
            object = clazz.getClass()
                    .getMethod("search", User.class,
                            String.class, long.class, int.class, Comparator[].class)
                    .invoke(
                            clazz,
                            user,
                            searchPhrase,
                            page,
                            AMOUNT_ELEMENTS_ON_PAGE,
                            comparator.length == 0 ? Comparator.naturalOrder() : comparator
                    );
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object == null ? subMenuItems: (List<ItemMenu>) object;
    }

    private void initCommands() {
        commands.put("\\d+", new Executer(user, this, key, currentPage));
        commands.put("^p\\s*\\d+", new Paginator(this, key));
        commands.put("^s\\s+.+", new Searcher(this, key, fromItemMenu, user, comparator));
        commands.put("(\\s*\\+\\s*)||(\\s*\\-\\s*)", new Order(this, key));
    }
}

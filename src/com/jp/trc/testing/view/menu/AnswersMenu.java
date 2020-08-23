package com.jp.trc.testing.view.menu;

import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.action.SelectAnswerAction;
import com.jp.trc.testing.view.action.UserAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu for display the variants answers test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 17.06.2020 8:11
 */
public class AnswersMenu {

    /**
     * List answers variants.
     */
    private List<Answer> answers;

    /**
     * List submenu items.
     */
    private List<ItemMenu> menuItems = new ArrayList<>();

    /**
     * List actions for each submenu item.
     */
    private List<UserAction> action;

    /**
     * Authorized user for whom the menu is formed.
     */
    private User user;

    /**
     * Constructor for creating a submenu.
     * @param user Authorized user for whom the menu is formed.
     * @param answers List answers variants.
     */
    public AnswersMenu(User user, List<Answer> answers) {
        this.user = user;
        this.answers = answers;
        createMenu();
    }

    /**
     * Displays a submenu with answers variants.
     */
    public void show(String questionTitle) {
        List<ItemMenu> menu = new ArrayList<ItemMenu>();
        action  = new ArrayList<>();

        int key = 0;
        for (ItemMenu item : menuItems) {
            action.add(key, item.getAction());
            item.setKey(++key);
            menu.add(item);
        }
        System.out.println("\t" + questionTitle);
        printMenu(menu);
    }

    /**
     * Executes action of a menu item with a key.
     * @param key Menu item.
     */
    public void select(int key) {
        this.action.get(key - 1).execute(user, 1);
    }

    private void printMenu(List<ItemMenu> menu) {
        menu.stream().filter(itemMenu -> itemMenu.getKey() != 0)
                .forEach(itemMenu -> System.out.println("\t" + itemMenu));
    }

    private void createMenu() {
        answers.forEach(answer -> menuItems.add(
                new ItemMenu(
                        answer.getTitle() + " - " + answer.isCorrect(),
                        user.getClass().getSimpleName(),
                        new SelectAnswerAction(answer)
                )
        ));
    }
}

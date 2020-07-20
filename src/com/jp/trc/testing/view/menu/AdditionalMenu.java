package com.jp.trc.testing.view.menu;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;

import java.util.*;

/**
 * Create additional menu.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 08.07.2020 21:19
 */
public class AdditionalMenu {

    private final int AMOUNT_ELEMENTS_ON_PAGE = 5;

    /**
     * If the menu items have more than "AMOUNT_ELEMENTS_ON_PAGE", then it is paginated.
     * @param subMenuItems Menu that need to divide into pages.
     * @return HashMap<String, List<ItemMenu>> for this menu.
     * Where key is the name of the page, and value is the content of this page.
     */
    public HashMap<String, List<ItemMenu>> createPagination(List<ItemMenu> subMenuItems) {
        HashMap<String, List<ItemMenu>> menuItems = new HashMap<>();

        if (subMenuItems.size() <= AMOUNT_ELEMENTS_ON_PAGE) {
            menuItems.put("p1", subMenuItems);
        } else {
            menuItems.put("p0", subMenuItems);

            int lastPage = subMenuItems.size() % AMOUNT_ELEMENTS_ON_PAGE;
            int amountPage = (lastPage == 0)
                    ? subMenuItems.size() / AMOUNT_ELEMENTS_ON_PAGE
                    : (subMenuItems.size() / AMOUNT_ELEMENTS_ON_PAGE) + 1;

            int fromIndex = 0;
            int toIndex = 0;
            for (int j = 1; j <= amountPage; j++) {
                fromIndex = (j == 1) ? 0 : fromIndex + AMOUNT_ELEMENTS_ON_PAGE;
                toIndex = (j == amountPage) ? subMenuItems.size() : toIndex + AMOUNT_ELEMENTS_ON_PAGE;
                menuItems.put("p" + j, subMenuItems.subList(fromIndex, toIndex));
            }
        }

        return menuItems;
    }

    /**
     * Searches and creates a new submenu.
     * @param user User for whom the search is being performed.
     * @param phrase Searched phrase.
     * @param subMenuItems The submenu from which the search was called.
     * @return New submenu.
     * @throws ObjectNotFoundException Thrown out if the search did not return any results.
     */
    public List<ItemMenu> search(User user, String phrase, List<ItemMenu> subMenuItems)
            throws ObjectNotFoundException {
        List<ItemMenu> resultSearch;

        if (user instanceof Student) {
            resultSearch = new ArrayList<>(searchTest(user, phrase, subMenuItems));
        } else {
            resultSearch = new ArrayList<>(searchUser(phrase, subMenuItems));
        }

        if (resultSearch.size() == 0) {
            throw new ObjectNotFoundException("Поиск не дал результатов!");
        }
        return resultSearch;
    }

    private List<ItemMenu> searchTest(User user, String phrase, List<ItemMenu> subMenuItems)
            throws ObjectNotFoundException {
        List<ItemMenu> resultSearch = new ArrayList<>();
        TestController testController = new TestController();

        for (ItemMenu itemMenu : subMenuItems) {
            StringBuilder text = new StringBuilder();
            for (Test test : testController.getTestsForStudent(user.getId())) {
                if (itemMenu.getItemName().equals(test.getTitle())) {
                    text.append(test.getTitle()).append(" ");
                    for (Question question : testController.getTestQuestions(test.getId())) {
                        text.append(question.getQuery()).append(" ");
                        for (Answer answer : testController.getAnswerVariants(question.getId())) {
                            text.append(answer.getTitle()).append(" ");
                        }
                    }
                }
            }
            if (text.toString().contains(phrase)) {
                resultSearch.add(itemMenu);
            }
        }
        return resultSearch;
    }

    private List<ItemMenu> searchUser(String phrase, List<ItemMenu> subMenuItems)
            throws ObjectNotFoundException {
        List<ItemMenu> resultSearch = new ArrayList<>();

        for (ItemMenu itemMenu : subMenuItems) {
            if (itemMenu.getItemName().contains(phrase)) {
                resultSearch.add(itemMenu);
            }
        }
        return resultSearch;
    }
}

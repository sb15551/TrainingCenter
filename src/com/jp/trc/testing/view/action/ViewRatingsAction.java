package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * List of students sorted by rating.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:35
 */
public class ViewRatingsAction implements UserAction, SubMenuForTeacher {

    /**
     * Controller for working with users.
     */
    private static UserController userController = new UserController();

    /**
     * Group id.
     */
    private int groupId;

    /**
     * Submenu with groups.
     */
    private SubMenu subMenu;

    /**
     * Constructor for creating a object.
     * @param groupId
     */
    public ViewRatingsAction(int groupId) {
        this.groupId = groupId;
    }

    /**
     * List of students sorted by rating.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user, int page) {
        subMenu = new SubMenu(
                user,
                "ВСЕ СТУДЕНТЫ",
                this,
                createSubMenu(user, 1, SubMenu.AMOUNT_ELEMENTS_ON_PAGE)
        );
        subMenu.show(page);
    }

    /**
     * Sorted students by rating.
     * @param students List of students to sort.
     */
    public static void sortStudetns(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = students.size() - 1; j > i; j--) {
                if (students.get(j - 1).getRating() > students.get(j).getRating()) {
                    Student tmp = students.get(j - 1);
                    students.set(j - 1, students.get(j));
                    students.set(j, tmp);
                }
            }
        }
    }

    /**
     * Get amount submenu pages.
     * @param user User for which the submenu is being created.
     * @param amountElementsOnPage Amount elements on page.
     * @return amount submenu pages.
     */
    public int getAmountSubmenuPages(User user, int amountElementsOnPage) {
        UserController controller = new UserController();
        int amountElements = controller
                .getGroupStudents(groupId, 0, amountElementsOnPage, Comparator.naturalOrder())
                .size();
        return (amountElements % amountElementsOnPage) == 0
                ? amountElements / amountElementsOnPage
                : (amountElements / amountElementsOnPage) + 1;
    }

    /**
     * Creating submenu.
     *
     * @param user                 User for which the submenu is being created.
     * @param page                 Page number to display.
     * @param amountElementsOnPage Amount elements on page.
     * @param comparator           Comparator for sorting.
     * @return List<ItemMenu> Submenu.
     */
    @Override
    public List<ItemMenu> createSubMenu(User user, long page,
                                        int amountElementsOnPage, Comparator... comparator) {
        List<Student> tmp = new ArrayList<>(
                userController.getGroupStudents(
                        groupId,
                        page,
                        amountElementsOnPage,
                        comparator.length == 0 ? Comparator.naturalOrder() : comparator[0]
                )
        );
        return createItemMenu(
                tmp,
                user
        );
    }

    /**
     * Search by specified parameters and create submenu.
     *
     * @param user                 User for which the submenu is being created.
     * @param phrase               Search phrase.
     * @param page                 Page number to display.
     * @param amountElementsOnPage Amount elements on page.
     * @param comparator           Comparator for sorting.
     * @return List<ItemMenu> Ready submenu.
     */
    @Override
    public List<ItemMenu> search(User user, String phrase, long page,
                                 int amountElementsOnPage, Comparator... comparator) {
        List<Student> tmp = new ArrayList<>(
                userController.searchStudentInGroup(
                        groupId,
                        phrase,
                        page,
                        amountElementsOnPage,
                        comparator.length == 0 ? Comparator.naturalOrder() : comparator[0]
                )
        );
        return createItemMenu(
                tmp,
                user
        );
    }

    /**
     * Create item menu.
     *
     * @param list List to create a submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu> Submenu.
     */
    @Override
    public List<ItemMenu> createItemMenu(List list, User user) {
        ViewRatingsAction.sortStudetns(list);
        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Student student : (List<Student>) list) {
            submenuItems.add(new ItemMenu(
                    String.format(
                            "%s   |   Rating: %s",
                            student.getName(),
                            Double.isNaN(student.getRating())
                                    ? "Студент еще не прошел ни одного теста"
                                    : String.format("%.1f", student.getRating())
                    ),
                    user.getClass().getSimpleName(),
                    new ViewStudentInfoAction(student)
            ));
        }
        return submenuItems;
    }
}

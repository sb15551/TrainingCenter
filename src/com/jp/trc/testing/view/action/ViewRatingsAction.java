package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * List of students sorted by rating.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:35
 */
public class ViewRatingsAction implements UserAction {

    /**
     * Submenu with groups.
     */
    private SubMenu subMenu;

    /**
     * List of students sorted by rating.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        subMenu = new SubMenu(user, "ВСЕ СТУДЕНТЫ", createSubMenu(user));
        subMenu.show();
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
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenu(User user) {
        UserController userController = new UserController();
        List<Student> students = userController.getAllStudents();
        sortStudetns(students);
        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Student student : students) {
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

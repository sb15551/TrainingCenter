package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of students sorted by rating.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:36
 */
public class ViewRatingByGroupAction implements UserAction {

    /**
     * Group id.
     */
    private int groupId;

    /**
     * Submenu with groups.
     */
    private SubMenu subMenu;

    /**
     * Controller for working with users.
     */
    private UserController userController = new UserController();

    /**
     * Default constructor.
     * Constructor for creating a object.
     */
    public ViewRatingByGroupAction() {

    }

    /**
     * Constructor for creating a object.
     * @param groupId
     */
    public ViewRatingByGroupAction(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Group of students sorted by rating.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        if (groupId == 0) {
            subMenu = new SubMenu(user, "СТУДЕНЧЕСКИЕ ГРУППЫ", createSubMenu(user));
            subMenu.show();
        } else {
            subMenu = new SubMenu(
                    user,
                    userController.getGroup(groupId).getTitle(),
                    createSubMenuForGroup(user, groupId)
            );
            subMenu.show();
        }
    }

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenu(User user) {
        List<ItemMenu> subMenuItems = new ArrayList<>();

        for (Group group : userController.getGroups()) {
            subMenuItems.add(new ItemMenu(
                    group.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewRatingByGroupAction(group.getId())
            ));
        }
        subMenuItems.add(new ItemMenu(
                "All students",
                user.getClass().getSimpleName(),
                new ViewRatingsAction()
        ));
        return subMenuItems;
    }

    /**
     * Creating submenu for group.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenuForGroup(User user, int groupId) {
        List<Student> students = userController.getGroupStudents(groupId);
        ViewRatingsAction.sortStudetns(students);
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

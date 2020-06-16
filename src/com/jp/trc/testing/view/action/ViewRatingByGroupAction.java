package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
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
     * Constructor for creating a object.
     */
    public ViewRatingByGroupAction(User user) {
        subMenu = new SubMenu(user, this.getClass().getSimpleName());
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
            subMenu.show();
        } else {
            List<User> users = new UserController().getAllUsers();
            List<Student> students = new ArrayList(
                    users.stream()
                            .filter(u -> u instanceof Student
                                    && ((Student) u).getGroupId() == groupId)
                            .collect(Collectors.toList())
            );
            ViewRatingsAction.sortStudetns(students);
            students.forEach(System.out::println);
        }
    }
}

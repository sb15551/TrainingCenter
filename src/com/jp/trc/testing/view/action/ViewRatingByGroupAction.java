package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            subMenu = new SubMenu(user, "РЕЙТИНГ СТУДЕНТОВ ПО ГРУППАМ", createSubMenu(user));
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

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenu(User user) {
        UserController controller = new UserController();
        List<ItemMenu> subMenuItems = new ArrayList<>();

        for (Group group : controller.getGroups()) {
            subMenuItems.add(new ItemMenu(
                    group.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewRatingByGroupAction(group.getId())
            ));
        }
        return subMenuItems;
    }
}

package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Lists of all users of this institution.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:35
 */
public class ViewListUsersAction implements UserAction {

    /**
     * Submenu with users.
     */
    private SubMenu subMenu;

    /**
     * Lists of all users of this institution.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        subMenu = new SubMenu(user, "СПИСОК ВСЕХ ПОЛЬЗОВАТЕЛЕЙ СИСТЕМЫ", createSubMenu(user));
        subMenu.show();
    }

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenu(User user) {
        List<ItemMenu> subMenuItems = new ArrayList<>();
        List<User> users = new UserController().getAllUsers();
        Collections.sort(users, Comparator.naturalOrder());

        for (User usr : users) {
            subMenuItems.add(new ItemMenu(
                    usr.getName(),
                    user.getClass().getSimpleName(),
                    new ViewUserInfoAction(usr)
            ));
        }

        return subMenuItems;
    }
}

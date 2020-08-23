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
public class ViewListUsersAction implements UserAction, SubMenuForTeacher {

    /**
     * Submenu with users.
     */
    private static SubMenu subMenu;

    /**
     * Controller for working with users.
     */
    private static UserController userController = new UserController();

    /**
     * Lists of all users of this institution.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user, int page) {
        subMenu = new SubMenu(
                user,
                "СПИСОК ВСЕХ ПОЛЬЗОВАТЕЛЕЙ СИСТЕМЫ",
                this,
                createSubMenu(user, 1, SubMenu.AMOUNT_ELEMENTS_ON_PAGE)
        );
        subMenu.show(page);
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

        Collections.sort(list, Comparator.naturalOrder());
        List<ItemMenu> subMenuItems = new ArrayList<>();
        for (User usr : (List<User>) list) {
            subMenuItems.add(new ItemMenu(
                    usr.getName(),
                    user.getClass().getSimpleName(),
                    new ViewUserInfoAction(usr)
            ));
        }
        return subMenuItems;
    }
}

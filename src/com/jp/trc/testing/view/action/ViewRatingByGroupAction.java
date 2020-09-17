package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.Filter;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Group of students sorted by rating.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:36
 */
public class ViewRatingByGroupAction implements UserAction, SubMenuForTeacher {

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
    public void execute(User user, int page) {
        subMenu = new SubMenu(
                user,
                "СТУДЕНЧЕСКИЕ ГРУППЫ",
                this,
                createSubMenu(user, new Filter(
                        0,
                        SubMenu.AMOUNT_ELEMENTS_ON_PAGE,
                        Comparator.naturalOrder()
                ))
        );
        if (groupId == 0) {
            subMenu.show(page);
        } else {
            ViewRatingsAction subMenuItems = new ViewRatingsAction(groupId);
            subMenu = new SubMenu(
                    user,
                    userController.getGroup(groupId).getTitle(),
                    subMenuItems,
                    subMenuItems.createSubMenu(user, subMenu.getFilter())
            );
            subMenu.show(subMenu.getCurrentPage());
        }
    }

    /**
     * Get amount submenu pages.
     *
     * @param user                 User for which the submenu is being created.
     * @param amountElementsOnPage Amount elements on page.
     * @return amount submenu pages.
     */
    @Override
    public int getAmountSubmenuPages(User user, int amountElementsOnPage) {
        int amountElements = userController
                .getGroups(new Filter(0, 0, Comparator.naturalOrder()))
                .size();
        return (amountElements % amountElementsOnPage) == 0
                ? amountElements / amountElementsOnPage
                : (amountElements / amountElementsOnPage) + 1;
    }

    /**
     * Creating submenu.
     *
     * @param user   User for which the submenu is being created.
     * @param filter Filter for paging, search and ordering.
     * @return List<ItemMenu> Submenu.
     */
    @Override
    public List<ItemMenu> createSubMenu(User user, Filter filter) {
        List<Group> tmp = new ArrayList<>(
                userController.getGroups(
                        filter
                )
        );
        Collections.sort(tmp, filter.getComparator());
        return createItemMenu(tmp, user);
    }

    /**
     * Search by specified parameters and create submenu.
     *
     * @param user                 User for which the submenu is being created.
     * @param filter Filter for paging, search and ordering.
     * @return List<ItemMenu> Ready submenu.
     */
    @Override
    public List<ItemMenu> search(User user, Filter filter) {
        List<Group> tmp = new ArrayList<>(
                userController.searchGroup(filter)
        );
        return createItemMenu(tmp, user);
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
        List<ItemMenu> subMenuItems = new ArrayList<>();
        for (Group group : (List<Group>) list) {
            subMenuItems.add(new ItemMenu(
                    group.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewRatingByGroupAction(group.getId())
            ));
        }
        subMenuItems.add(new ItemMenu(
                "All students",
                user.getClass().getSimpleName(),
                new ViewRatingsAction(0)
        ));
        return subMenuItems;
    }
}

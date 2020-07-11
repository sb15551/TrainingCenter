package com.jp.trc.testing.view.menu;

import java.util.HashMap;
import java.util.List;

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
}

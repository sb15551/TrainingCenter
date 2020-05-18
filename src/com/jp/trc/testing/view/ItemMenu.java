package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.UserAction;

/**
 * Menu item and action for it.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class ItemMenu {

    /**
     * Name menu item.
     */
    private String itemName;

    /**
     * Type of user for which the menu item will be displayed.
     */
    private String typeUser;

    /**
     * Action for menu item.
     */
    private UserAction action;

    /**
     * Unique menu item key.
     */
    private int key;

    public ItemMenu(int key, String itemName, String typeUser, UserAction action) {
        this.key = key;
        this.itemName = itemName;
        this.typeUser = typeUser;
        this.action = action;
    }

    public ItemMenu(String itemName, String typeUser, UserAction action) {
        this.itemName = itemName;
        this.typeUser = typeUser;
        this.action = action;
    }

    /**
     * Gets type
     *
     * @return value of type java.lang.String
     */
    public String getTypeUser() {
        return typeUser;
    }

    /**
     * Gets item
     *
     * @return value of item type java.lang.String
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets key
     *
     * @return value of key int
     */
    public int getKey() {
        return key;
    }

    /**
     * Gets action
     *
     * @return value of action com.jp.trc.testing.controller.UserAction
     */
    public UserAction getAction() {
        return action;
    }

    /**
     * Sets value key
     */
    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return String.format("%s. %s", key, itemName);
    }
}

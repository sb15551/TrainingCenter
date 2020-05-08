package view;

import controller.UserAction;

public class ItemMenu {
    private String item;
    private String type;
    private UserAction action;
    private int key;

    public ItemMenu(int key, String item, String type, UserAction action) {
        this.key = key;
        this.item = item;
        this.type = type;
        this.action = action;
    }

    public ItemMenu(String item, String type, UserAction action) {
        this.item = item;
        this.type = type;
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public String getItem() {
        return item;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public UserAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return String.format("%s. %s", key, item);
    }
}

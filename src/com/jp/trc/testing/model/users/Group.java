package com.jp.trc.testing.model.users;

/**
 * Group students.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 03.06.2020 8:28
 */
public class Group {

    /**
     * Group id.
     */
    private int id;

    /**
     * Group title.
     */
    private String title;

    /**
     * Constructor for creating an object.
     * @param id Group id.
     * @param title Group title.
     */
    public Group(int id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Gets id.
     *
     * @return value of id int
     */
    public int getId() {
        return id;
    }

    /**
     * Gets title.
     *
     * @return value of title java.lang.String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets value title.
     *
     * @param title value of title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return title;
    }
}

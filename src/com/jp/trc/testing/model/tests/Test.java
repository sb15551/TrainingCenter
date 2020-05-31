package com.jp.trc.testing.model.tests;

import com.jp.trc.testing.model.users.Teacher;

import java.util.List;

/**
 * Student Test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Test {

    /**
     * Test id.
     */
    private int id;

    /**
     * Test title.
     */
    private String title;

    /**
     * Test author.
     */
    private Teacher author;

    public Test(int id, String title, Teacher author) {
        this.id = id;
        this.title = title;
        this.author = author;
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
     * Sets value id.
     *
     * @param id value of id
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets author.
     *
     * @return value of author com.jp.trc.testing.model.users.Teacher
     */
    public Teacher getAuthor() {
        return author;
    }

    /**
     * Sets value author.
     *
     * @param author value of author
     */
    public void setAuthor(Teacher author) {
        this.author = author;
    }

    /**
     * Returns a string representation of the object in format "Title | Name author"
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("%-20s\t|\t%-20s", title, author.getName());
    }
}

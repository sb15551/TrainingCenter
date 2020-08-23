package com.jp.trc.testing.model.tests;

import com.jp.trc.testing.model.users.Teacher;

/**
 * Student Test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Test implements Comparable<Test> {

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

    /**
     * Max score for passing the test.
     */
    private int passingScore;

    /**
     * Constructor for creating an object.
     * @param id Test id.
     * @param title Test title.
     * @param author Test author.
     * @param passingScore Max score for passing the test.
     */
    public Test(int id, String title, Teacher author, int passingScore) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.passingScore = passingScore;
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
     * Gets passingScore.
     *
     * @return value of passingScore int
     */
    public int getPassingScore() {
        return passingScore;
    }

    /**
     * Sets value passingScore.
     *
     * @param passingScore value of passingScore
     */
    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }

    /**
     * Returns a string representation of the object in format "Title | Name author"
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("%-20s\t|\t%-20s", title, author.getName());
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Test o) {
        return this.title.compareTo(o.getTitle());
    }
}

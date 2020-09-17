package com.jp.trc.testing.model.tests;

import java.io.Serializable;

/**
 * Question for test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Question implements Serializable {

    /**
     * Question id.
     */
    private int id;

    /**
     * Test id to which the question relates.
     */
    private int testId;

    /**
     * Question text.
     */
    private String query;

    /**
     * Constructor for creating an object.
     * @param id Question id.
     * @param testId Test id to which the question relates.
     * @param query Question text.
     */
    public Question(int id, int testId, String query) {
        this.id = id;
        this.testId = testId;
        this.query = query;
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
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets query
     *
     * @return value of query java.lang.String
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets value query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets testId.
     *
     * @return value of testId int
     */
    public int getTestId() {
        return testId;
    }

    /**
     * Sets value testId.
     *
     * @param testId value of testId
     */
    public void setTestId(int testId) {
        this.testId = testId;
    }
}

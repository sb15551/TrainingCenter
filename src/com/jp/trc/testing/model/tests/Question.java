package com.jp.trc.testing.model.tests;

import java.util.List;

/**
 * Question for test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Question {

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
}

package com.jp.trc.testing.model.tests;

/**
 * Student assigned a test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Assignment {

    /**
     * Student id.
     */
    private int studentId;

    /**
     * Test id.
     */
    private int testId;

    /**
     * Student test result.
     */
    private int result;

    /**
     * Constructor for creating an object.
     * @param studentId Student id.
     * @param testId Test id.
     * @param result Student test result.
     */
    public Assignment(int studentId, int testId, int result) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
    }

    /**
     * Gets studentId
     *
     * @return value of studentId int
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Gets testId
     *
     * @return value of testId int
     */
    public int getTestId() {
        return testId;
    }

    /**
     * Gets result
     *
     * @return value of result int
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets value result
     */
    public void setResult(int result) {
        this.result = result;
    }
}

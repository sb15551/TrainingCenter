package com.jp.trc.testing.model.testing;

public class Subscription {
    private int studentId;
    private int testId;
    private int result;

    public Subscription(int studentId, int testId) {
        this.studentId = studentId;
        this.testId = testId;
    }

    public Subscription(int studentId, int testId, int result) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getTestId() {
        return testId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

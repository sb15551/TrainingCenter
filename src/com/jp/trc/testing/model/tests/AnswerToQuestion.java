package com.jp.trc.testing.model.tests;

/**
 * Student's answer to the question in the course of passing the test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 31.05.2020 11:03
 */
public class AnswerToQuestion {

    /**
     * Student id.
     */
    private int studentId;

    /**
     * Asnwer id that the student chose.
     */
    private int answerId;

    public AnswerToQuestion(int studentId, int answerId) {
        this.studentId = studentId;
        this.answerId = answerId;
    }

    /**
     * Gets studentId.
     *
     * @return value of studentId int
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets value studentId.
     *
     * @param studentId value of studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets answerId.
     *
     * @return value of answerId int
     */
    public int getAnswerId() {
        return answerId;
    }

    /**
     * Sets value answerId.
     *
     * @param answerId value of answerId
     */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}

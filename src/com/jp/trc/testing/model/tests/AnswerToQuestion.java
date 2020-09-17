package com.jp.trc.testing.model.tests;

import java.io.Serializable;
import java.util.Objects;

/**
 * Student's answer to the question in the course of passing the test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 31.05.2020 11:03
 */
public class AnswerToQuestion implements Serializable {

    /**
     * Student id.
     */
    private int studentId;

    /**
     * Asnwer id that the student chose.
     */
    private int answerId;

    /**
     * Constructor for creating an object.
     * @param studentId Student id.
     * @param answerId Asnwer id that the student chose.
     */
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

    /**
     * Comparing object by  student id and answer id.
     * @param o Object to compare.
     * @return true if the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnswerToQuestion that = (AnswerToQuestion) o;
        return studentId == that.studentId
                && answerId == that.answerId;
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(studentId, answerId);
    }
}

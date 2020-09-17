package com.jp.trc.testing.model.tests;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 29.05.2020 12:23
 */
public class Answer implements Serializable {

    /**
     * Answer id.
     */
    private int id;

    /**
     * Question id to which the answer relates.
     */
    private int questionId;

    /**
     * Answer text.
     */
    private String title;

    /**
     * Correct answer.
     */
    private boolean correct;

    /**
     * Constructor for creating an object.
     * @param id Answer id.
     * @param questionId Question id to which the answer relates.
     * @param title Answer text.
     * @param correct Correct answer.
     */
    public Answer(int id, int questionId, String title, boolean correct) {
        this.id = id;
        this.questionId = questionId;
        this.title = title;
        this.correct = correct;
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
     * Gets questionId.
     *
     * @return value of questionId int
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * Sets value questionId.
     *
     * @param questionId value of questionId
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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
     * Gets correct.
     *
     * @return value of correct boolean
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Sets value correct.
     *
     * @param correct value of correct
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    /**
     * Compared answer by id.
     * @param o answer to compare.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id;
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

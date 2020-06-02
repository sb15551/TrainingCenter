package com.jp.trc.testing.model.tests;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 29.05.2020 12:23
 */
public class Answer {

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
}

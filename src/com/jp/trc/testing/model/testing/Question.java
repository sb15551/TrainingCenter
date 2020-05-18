package com.jp.trc.testing.model.testing;

import java.util.List;

/**
 * Question for test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Question {

    /**
     * Question.
     */
    private String query;

    /**
     * Answer variants.
     */
    private List<String> answers;

    /**
     * Correct answer.
     */
    private int correctAnswer;

    public Question(String query, List<String> answers, int correctAnswer) {
        this.query = query;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
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
     * Gets answers
     *
     * @return value of answers java.util.List<java.lang.String>
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Sets value answers
     */
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * Gets correctAnswer
     *
     * @return value of correctAnswer int
     */
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets value correctAnswer
     */
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

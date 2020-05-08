package com.jp.trc.testing.model.testing;

import java.util.List;

public class Question {
    private String query;
    private List<String> answers;
    private int correctAnswer;

    public Question(String query, List<String> answers, int correctAnswer) {
        this.query = query;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

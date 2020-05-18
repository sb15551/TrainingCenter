package com.jp.trc.testing.model.testing;

import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;

import java.util.List;

/**
 * Student Test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Test {

    /**
     * Test id.
     */
    private int id;

    /**
     * Test title.
     */
    private String title;

    /**
     * Test questions.
     */
    private List<Question> questions;

    /**
     * Test author.
     */
    private Teacher author;

    public Test(int id, String title, List<Question> questions, Teacher author) {
        this.id = id;
        this.title = title;
        this.questions = questions;
        this.author = author;
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
     * Gets title.
     *
     * @return value of title java.lang.String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets value title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets questions.
     *
     * @return value of questions java.util.List<com.jp.trc.testing.model.testing.Question>.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Adding question to the test.
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }

    /**
     * Gets author.
     *
     * @return value of author com.jp.trc.testing.model.users.Teacher
     */
    public Teacher getAuthor() {
        return author;
    }

    /**
     * Sets value author.
     */
    public void setAuthor(Teacher author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("%-20s\t|\t%-20s", title, author.getName());
    }
}

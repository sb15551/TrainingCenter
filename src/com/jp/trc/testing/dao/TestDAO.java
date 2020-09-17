package com.jp.trc.testing.dao;

import com.jp.trc.testing.model.tests.*;

import java.util.List;
import java.util.Set;

/**
 * Database interface.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 13.09.2020 10:43
 */
public interface TestDAO {

    /**
     * Get list all tests.
     * @return List<Test> all tests.
     */
    public List<Test> getTests();

    /**
     * Get list questions of all tests.
     * @return List<Question> questions of all tests.
     */
    public List<Question> getQuestions();

    /**
     * Get list all answer options on all tests.
     * @return List<Answer> all answer options on all tests.
     */
    public List<Answer> getAnswers();

    /**
     * Get the student's attitude to the test.
     * @param studentId Student id who is taking the test.
     * @param testId Test id of the test the student is taking.
     * @return Assignment the student's to the test.
     */
    public Assignment getAssignment(int studentId, int testId);

    /**
     * Update test results record.
     * @param assignment
     */
    public void updateAssignment(Assignment assignment);

    /**
     * Get a test by to his id.
     * @param testId
     * @return
     */
    public Test getTest(int testId);

    /**
     * Get an answer option by its id.
     * @param answerId
     * @return
     */
    public Answer getAnswer(int answerId);

    /**
     * Remembers the student's answer to a question during the test.
     * @param answerToQuestion student's answer to a question
     * @return true if the entry was successfully added.
     */
    public boolean addAnswerToQuestion(AnswerToQuestion answerToQuestion);

    /**
     * Get all answers to all students' questions.
     * @return Set<AnswerToQuestion> all answers to all students' questions.
     */
    public Set<AnswerToQuestion> getAnswerToQuestions();

    /**
     * Deletes student answers to the question that he put earlier.
     * @param studentId Student id whose answers need to delete.
     * @param questionId Question id answers to be deleted.
     */
    public void clearAnswerToQuestion(int studentId, int questionId);
}

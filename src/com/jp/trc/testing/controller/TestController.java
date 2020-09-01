package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.*;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;
import com.jp.trc.testing.view.menu.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for working with tests.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 27.05.2020 9:20
 */
public class TestController {

    /**
     * Tests compiled by a teacher.
     * @param teacherId Teacher id who compiled tests.
     * @param filter Filter for paging, search and ordering.
     * @return Tests compiled by a teacher.
     */
    public List<Test> getTestsByTeacher(int teacherId, Filter filter) {
        List<Test> tests = Repository.getTests().stream()
                .filter(test -> test.getAuthor().getId() == teacherId)
                .collect(Collectors.toList());
        Collections.sort(tests, filter.getComparator());

        if (filter.getLimit() == 0) {
            return tests;
        } else {
            return tests.stream()
                    .filter(test -> test.getAuthor().getId() == teacherId)
                    .skip(filter.getOffset())
                    .limit(filter.getLimit())
                    .collect(Collectors.toList());
        }
    }

    /**
     * List of tests available to the user.
     * @param studentId User id for which to get a list of tests.
     * @param filter Filter for paging, search and ordering.
     * @return Tests available to user.
     */
    public List<Test> getTestsForStudent(int studentId, Filter filter) {
        List<Test> tests = new ArrayList<>();
        List<Assignment> assignments = Repository.getAssignments().stream()
                .filter(test -> test.getStudentId() == studentId)
                .collect(Collectors.toList());

        for (Assignment assignment : assignments) {
            for (Test test : Repository.getTests()) {
                if (assignment.getTestId() == test.getId()) {
                    tests.add(test);
                }
            }
        }
        Collections.sort(tests, filter.getComparator());

        if (filter.getLimit() == 0) {
            return tests;
        } else {
            return tests.stream()
                    .skip(filter.getOffset())
                    .limit(filter.getLimit())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Get amount tests for student.
     * @param studentId User id for which to get a list of tests.
     * @return amount.
     */
    public int getAmountTestsForStudent(int studentId) {
        return (int) Repository.getAssignments().stream()
                .filter(test -> test.getStudentId() == studentId)
                .count();
    }

    /**
     * Executing search for to the tests.
     * @param user User for which the search is being executed.
     * @param filter Filter for paging, search and ordering.
     * @return List<Test> Ready submenu.
     */
    public List<Test> searchTest(User user, Filter filter) {
        List<Test> submenuItems = new ArrayList<>();
        List<Test> tests = new ArrayList<>();
        if (user instanceof Teacher) {
            tests = getTestsByTeacher(user.getId(), new Filter(0, 0, Comparator.naturalOrder()));
        }
        if (user instanceof Student) {
            tests = getTestsForStudent(user.getId(), new Filter(0, 0, Comparator.naturalOrder()));
        }
        for (Test test : tests) {
            StringBuilder text = new StringBuilder();
            text.append(test.getTitle()).append(" ");
            for (Question question : getTestQuestions(test.getId())) {
                text.append(question.getQuery()).append(" ");
                for (Answer answer : getAnswerVariants(question.getId())) {
                    text.append(answer.getTitle()).append(" ");
                }
            }
            if (text.toString().contains(filter.getSearchPhrase())) {
                submenuItems.add(test);
            }
        }
        return submenuItems.stream()
                .skip(filter.getOffset())
                .limit(filter.getLimit())
                .collect(Collectors.toList());
    }

    /**
     * Get test by id.
     * @param testId Test id.
     * @return test by id.
     * @throws ObjectNotFoundException Exception is thrown if sought-for test not found.
     */
    public Test getTest(int testId) throws ObjectNotFoundException {
        for (Test test : Repository.getTests()) {
            if (test.getId() == testId) {
                return test;
            }
        }
        throw new ObjectNotFoundException("Such test not found!!!");
    }

    /**
     * Get question quentity in test.
     * @param testId Test id.
     * @return question quentity in test.
     */
    public int getQuestionQuentityInTest(int testId) {
        return Repository.getQuestions().stream()
                .filter(question -> question.getTestId() == testId)
                .collect(Collectors.toList())
                .size();
    }

    /**
     * Get test questions.
     * @param testId Test id.
     * @return questions in test.
     */
    public List<Question> getTestQuestions(int testId) {
        return Repository.getQuestions().stream()
                .filter(question -> question.getTestId() == testId)
                .collect(Collectors.toList());
    }

    /**
     * Get answers to a question.
     * @param questionId Id of question whose answer options to get.
     * @return list answer variants.
     */
    public List<Answer> getAnswerVariants(int questionId) {
        return Repository.getAnswers().stream()
                .filter(answer -> answer.getQuestionId() == questionId)
                .collect(Collectors.toList());
    }

    /**
     * Get the test id assigned to the student.
     * @param studentId Student id.
     * @param testId Test id.
     * @return student-test attitude.
     */
    public Assignment getAssignment(int studentId, int testId) {
        return Repository.getAssignment(studentId, testId);
    }

    /**
     * Get answer.
     * @param answerId Id answer to get.
     * @return answer.
     * @throws ObjectNotFoundException Exception is thrown if sought-for answer not found.
     */
    public Answer getAnswer(int answerId) throws ObjectNotFoundException {
        for (Answer answer : Repository.getAnswers()) {
            if (answer.getId() == answerId) {
                return answer;
            }
        }
        throw new ObjectNotFoundException("Such answer not found!!!");
    }

    /**
     * Tests results which compiled teacher.
     * @param testId Test id, result which to need get.
     * @return Test results for each student.
     */
    public List<Assignment> getResultTests(int testId) {
        List<Assignment> assignments = new ArrayList<>();
        for (Assignment assignment : Repository.getAssignments()) {
            if (assignment.getTestId() == testId) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    /**
     * Answer to the question the student answered during the test.
     * @param userId Student id who passes the test.
     * @param answerId Id answer that the student chose during the test.
     * @return true if the response was successfully added.
     */
    public boolean addAnswerToQuestion(int userId, int answerId) {
        return  Repository.addAnswerToQuestion(
                new AnswerToQuestion(userId, answerId)
        );
    }

    /**
     * Get student answers to a specific question.
     * @param userId Student id whose answers need to be received.
     * @param questionId Id question to which the student answered.
     * @return list of answers that the student gave to a specific question.
     */
    public List<Answer> getAnswersToQuestion(int userId, int questionId) {
        List<Answer> answersToTest = getAnswerVariants(questionId);
        List<Answer> studentAnswers = new ArrayList<>();
        Repository.getAnswerToQuestions().stream()
                .filter(answerToQuestion -> answerToQuestion.getStudentId() == userId)
                .filter(
                        answerToQuestion -> answersToTest
                        .contains(getAnswer(answerToQuestion.getAnswerId()))
                )
                .forEach(answer -> studentAnswers.add(getAnswer(answer.getAnswerId())));
        return studentAnswers;
    }

    /**
     * Get all the correct answers in the test question.
     * @param questionId Id of question you want to get the correct answers.
     * @return list of correct answers.
     */
    public List<Answer> getCorrectAnswersToQuestion(int questionId) {
        return getAnswerVariants(questionId)
                .stream()
                .filter(Answer::isCorrect)
                .collect(Collectors.toList());
    }

    /**
     * Get the correct answers to the specific question that the student noted.
     * @param studentId Student who is taking the test.
     * @param questionId Question id whose answers need to get.
     * @return list of correct answers that the student noted.
     */
    public List<Answer> getCorrectAnswersStudent(int studentId, int questionId) {
        List<Answer> correctAnswersStudent = new ArrayList<>();
        Repository.getAnswerToQuestions().stream()
                .filter(answerToQuestion -> answerToQuestion.getStudentId() == studentId)
                .filter(answerToQuestion -> getAnswer(answerToQuestion.getAnswerId()).isCorrect())
                .filter(
                        answerToQuestion -> getCorrectAnswersToQuestion(questionId)
                        .contains(getAnswer(answerToQuestion.getAnswerId()))
                )
                .forEach(
                        answerToQuestion -> correctAnswersStudent.add(
                                getAnswer(answerToQuestion.getAnswerId())
                        )
                );
        return correctAnswersStudent;
    }

    /**
     * Get all the incorrect answers in the test question.
     * @param questionId Id of question you want to get the incorrect answers.
     * @return list of incorrect answers.
     */
    public List<Answer> getIncorrectAnswersToQuestion(int questionId) {
        return getAnswerVariants(questionId)
                .stream()
                .filter(answer -> !answer.isCorrect())
                .collect(Collectors.toList());
    }

    /**
     * Get the incorrect answers to the specific question that the student noted.
     * @param studentId Student who is taking the test.
     * @param questionId Question id whose answers need to get.
     * @return list of incorrect answers that the student noted.
     */
    public List<Answer> getIncorrectAnswersStudent(int studentId, int questionId) {
        List<Answer> incorrectAnswersStudent = new ArrayList<>();
        Repository.getAnswerToQuestions().stream()
                .filter(answerToQuestion -> answerToQuestion.getStudentId() == studentId)
                .filter(answerToQuestion -> !getAnswer(answerToQuestion.getAnswerId()).isCorrect())
                .filter(
                        answerToQuestion -> getIncorrectAnswersToQuestion(questionId)
                                .contains(getAnswer(answerToQuestion.getAnswerId()))
                )
                .forEach(
                        answerToQuestion -> incorrectAnswersStudent.add(
                                getAnswer(answerToQuestion.getAnswerId())
                        )
                );
        return incorrectAnswersStudent;
    }

    /**
     * Calculates the percentage of correct answers.
     * First, he counts the number of correct answers that the student noted.
     * Then he considers the number of incorrect answers that the student noted.
     * @param userId student id who is passing the test.
     * @param testId test id.
     * @return if the percentage of incorrect answers is greater than the correct ones returns 0,
     * if less, then the percentage  difference in correct answers and not correct is returned.
     */
    public int calculateTestResult(int userId, int testId) {
        int correctAnswerToTest = 0;
        int correctAnswersStudent = 0;

        int inCorrectAnswerToTest = 0;
        int inCorrectAnswersStudent = 0;

        List<Question> questions = getTestQuestions(testId);

        for (Question question : questions) {
            List<Answer> correctAnswersToQuestion = getCorrectAnswersToQuestion(question.getId());

            correctAnswerToTest += correctAnswersToQuestion.size();
            correctAnswersStudent += getCorrectAnswersStudent(userId, question.getId()).size();

            List<Answer> inCorrectAnswersToTest = getIncorrectAnswersToQuestion(question.getId());

            inCorrectAnswerToTest += inCorrectAnswersToTest.size();
            inCorrectAnswersStudent += getIncorrectAnswersStudent(userId, question.getId()).size();
        }

        int percentageCorrectAnswers = (correctAnswersStudent * 100) / correctAnswerToTest;
        int percentageIncorrectAnswers = (inCorrectAnswersStudent * 100) / inCorrectAnswerToTest;

        return percentageCorrectAnswers < percentageIncorrectAnswers
                ? 0 : percentageCorrectAnswers - percentageIncorrectAnswers;
    }

    /**
     * Deletes student answers to the question that he put earlier.
     * @param studentId Student id whose answers need to delete.
     * @param questionId Question id answers to be deleted.
     */
    public void clearAnswerToQuestion(int studentId, int questionId) {
        Repository.getAnswerToQuestions().removeIf(
                answerToQuestion -> answerToQuestion.getStudentId() == studentId
                && getAnswer(answerToQuestion.getAnswerId()).getQuestionId() == questionId
        );
    }
}

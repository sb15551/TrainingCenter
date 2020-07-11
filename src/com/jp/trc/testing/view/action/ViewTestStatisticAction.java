package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Shows statistics test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.06.2020 7:49
 */
public class ViewTestStatisticAction implements UserAction {

    /**
     * Test id whose statistics need to show.
     */
    private int testId;

    /**
     * Submenu with tests.
     */
    private SubMenu subMenu;

    /**
     * Controller for accessing the database.
     */
    private TestController testController = new TestController();

    /**
     * Constructor for initializing and displaying the menu.
     * @param user User for whom need to display the menu.
     */
    public ViewTestStatisticAction(User user) {
        subMenu = new SubMenu(user, this.getClass().getSimpleName());
    }

    /**
     * Constructor for displaying test statistics.
     * @param testId Test id, statistics which need to get.
     */
    public ViewTestStatisticAction(int testId) {
        this.testId = testId;
    }

    /**
     * If the test is not selected, it displays a menu with tests.
     * If a test is selected, then show statistics test.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        if (testId == 0) {
            subMenu.show();
        } else {
            Test test = testController.getTest(testId);
            Integer testResult = testController.getAssignment(user.getId(), testId).getResult();
            if (testResult == null) {
                System.out.println(test.getTitle() + " еще ни разу не проходили");
            } else {
                int passingScore = test.getPassingScore();

                List<Question> questions = testController.getTestQuestions(testId);

                String passed = testResult >= passingScore ? "Пройден" : "Не пройден";

                System.out.println(test.getTitle());
                System.out.printf("Результаты теста: %s (%s)\n", testResult, passed);
                System.out.println("Результаты вопросов теста:");

                for (Question question : questions) {
                    List<Answer> answerVariants = testController
                            .getAnswerVariants(question.getId());

                    int countCorrectAnswers = testController
                            .getCorrectAnswersStudent(user.getId(), question.getId())
                            .size();
                    int countIncorrectAnswers = testController
                            .getIncorrectAnswersStudent(user.getId(), question.getId())
                            .size();

                    System.out.printf(
                            "\t%s - %s\n",
                            question.getQuery(),
                            getResultQuestion(
                                    answerVariants,
                                    countCorrectAnswers,
                                    countIncorrectAnswers
                            )
                    );
                }
                System.out.println("Процент правильных ответов: "
                        + testController.calculateTestResult(user.getId(), testId));
            }
            subMenu = new SubMenu(user, this.getClass().getSimpleName());
            subMenu.show();
        }
    }

    private String getResultQuestion(List<Answer> answers, int correctCount, int incorrectCount) {
        String resultQuestion = "";
        List<Answer> correctAnswers = answers.stream()
                .filter(Answer::isCorrect)
                .collect(Collectors.toList());

        if (correctCount == correctAnswers.size() && incorrectCount == 0) {
            resultQuestion = "Правильно";
        } else if (incorrectCount >= correctCount && incorrectCount >= 0) {
            resultQuestion = "Не правильно";
        } else if (correctCount <= correctAnswers.size() || incorrectCount != 0) {
            resultQuestion = "Частично правильно";
        } else {
            resultQuestion = "Вопрос пропущен";
        }
        return resultQuestion;
    }
}

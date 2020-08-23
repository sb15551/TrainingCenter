package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Shows statistics test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.06.2020 7:49
 */
public class ViewTestStatisticAction implements UserAction, SubMenuForStudents {

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
     * Default constructor.
     * Constructor for creating a object.
     */
    public ViewTestStatisticAction() {

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
    public void execute(User user, int page) {
        subMenu = new SubMenu(
                user,
                "ВЫБОР ТЕСТА ДЛЯ ПРОСМОТРА СТАТИСТИКИ",
                this,
                createSubMenu(user, 1, SubMenu.AMOUNT_ELEMENTS_ON_PAGE)
        );
        if (testId == 0) {
            subMenu.show(page);
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
            subMenu.show(page);
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

    /**
     * Create item menu.
     *
     * @param list List to create a submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu> Submenu.
     */
    @Override
    public List<ItemMenu> createItemMenu(List list, User user) {
        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Test test : (List<Test>) list) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new ViewTestStatisticAction(test.getId())
            ));
        }
        return submenuItems;
    }
}

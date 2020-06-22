package com.jp.trc.testing.view.passagetest;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.Input;
import com.jp.trc.testing.view.input.InputValidator;

import java.util.List;

/**
 * Class for passing the test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 21:57
 */
public class PassageTest {

    /**
     * Test to pass.
     */
    private int testId;

    /**
     * Student who will take the test.
     */
    private User user;

    /**
     * Keyboard input.
     */
    private Input input = new InputValidator(new ConsoleInput());

    /**
     * Controller to access the database.
     */
    private TestController testController = new TestController();

    /**
     * Controller to access the database.
     */
    private UserController userController = new UserController();

    /**
     * Constructor for creating a test passing.
     * @param user Student who will take the test.
     * @param testId Test to pass.
     */
    public PassageTest(User user, int testId) {
        this.user = user;
        this.testId = testId;
    }

    /**
     * Method for test begin.
     */
    public void beginTest() {
        int number = 1;
        for (Question question : testController.getTestQuestions(testId)) {
            List<Answer> answers = testController.getAnswerVariants(question.getId());
            testController.clearAnswerToQuestion(user.getId(), question.getId());

            AnswersMenu answersMenu = new AnswersMenu(user, answers);

            answersMenu.show(String.format("Вопрос №%s: %s", number++, question.getQuery()));

            int[] selectAnswers = input.askNumberAnswer("Введите номер ответа. "
                    + "\nЕсли ответов несколько введите их через пробел: ", answers.size());

            for (int numberAnswer : selectAnswers) {
                answersMenu.select(numberAnswer);
            }
        }
        endTest();
    }

    /**
     * Method for test end.
     */
    private void endTest() {
        System.out.println("\nТест завершен!");
        int resultTest = Math.round(testController.calculateTestResult(user.getId(), testId) / 10);
        System.out.printf("Результат теста: %s", resultTest);
        testController.getAssignment(user.getId(), testId).setResult(resultTest);
        userController.calculateStudentRating(user.getId());
    }
}

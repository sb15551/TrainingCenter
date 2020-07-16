package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.Input;
import com.jp.trc.testing.view.input.InputValidator;
import com.jp.trc.testing.view.menu.ItemMenu;
import com.jp.trc.testing.view.menu.SubMenu;
import com.jp.trc.testing.view.menu.AnswersMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a menu with tests or starts a test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 21:09
 */
public class SelectTestAction implements UserAction {

    /**
     * Test id for passing.
     */
    private int testId;

    /**
     * Submenu with tests.
     */
    private SubMenu subMenu;

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
     * Default constructor.
     * Constructor for creating a object.
     */
    public SelectTestAction() {
    }

    /**
     * Constructor for creating a object.
     * Called to pass the test.
     */
    public SelectTestAction(int testId) {
        this.testId = testId;
    }

    /**
     * If the test is not selected, it displays a menu with tests for passing.
     * If a test is selected, then the test begins.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        if (testId == 0) {
            subMenu = new SubMenu(user, "ВЫБОР ТЕСТА ДЛЯ ПРОХОЖДЕНИЯ", createSubMenu(user));
            subMenu.show();
        } else {
            userController = new UserController();
            testController = new TestController();
            input = new InputValidator(new ConsoleInput());

            beginTest(user);
            endTest(user);
        }
    }

    /**
     * Method for test begin.
     */
    private void beginTest(User user) {
        System.out.println(testController.getTest(testId).getTitle());
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
    }

    /**
     * Method for test end.
     */
    private void endTest(User user) {
        System.out.println("\nТест завершен!");
        int resultTest = Math.round(testController.calculateTestResult(user.getId(), testId) / 10);
        System.out.printf("Результат теста: %s", resultTest);
        testController.getAssignment(user.getId(), testId).setResult(resultTest);
        userController.calculateStudentRating(user.getId());
    }

    /**
     * Creating submenu.
     * @param user User for which the submenu is being created.
     * @return List<ItemMenu>
     */
    private List<ItemMenu> createSubMenu(User user) {
        TestController controller = new TestController();
        List<ItemMenu> submenuItems = new ArrayList<>();
        for (Test test : controller.getTestsForStudent(user.getId())) {
            submenuItems.add(new ItemMenu(
                    test.getTitle(),
                    user.getClass().getSimpleName(),
                    new SelectTestAction(test.getId())
            ));
        }
        return submenuItems;
    }
}

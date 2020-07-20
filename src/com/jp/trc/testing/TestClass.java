package com.jp.trc.testing;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.*;
import com.jp.trc.testing.view.*;
import com.jp.trc.testing.view.input.ConsoleInput;
import com.jp.trc.testing.view.input.InputValidator;

import java.util.Random;

/**
 * Test class.
 * Here are created users and tests.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class TestClass {

    /**
     * The main method of operation of the program.
     * This is where created users, groups, tests, and questions and answers.
     * @param args Argument list.
     */
    public static void main(String[] args) {
        addUsers();
        addTests();
        new UILauncher(new InputValidator(new ConsoleInput())).init();
    }

    private static void addUsers() {
        Repository.addGroup(new Group(1, "Group 1"));
        Repository.addGroup(new Group(2, "Group 2"));
        Repository.addGroup(new Group(3, "Group 3"));

        int userId = 0;
        Repository.addUser(new Student(userId++, 1, "Тестов Тест Тестович", "test", "test", 30));
        Repository.addUser(new Student(userId++, 1, "Иванов Иван Иванович", "ivan", "ivan", 25));
        Repository.addUser(new Student(userId++, 2, "Петров Петр Петрович", "petr", "petr", 18));
        Repository.addUser(new Student(userId++, 2, "Кузьмина Алевтина Кимовна", "alev", "alev", 20));
        Repository.addUser(new Student(userId++, 3, "Родионова Габи Иринеевна", "gabi", "gabi", 35));
        Repository.addUser(new Student(userId++, 3, "Сидоров Сидр Сидорович", "sidr", "sidr", 21));
        Repository.addUser(new Student(userId++, 1, "Иванив Устин Фёдорович", "ustin", "ustin", 25));
        Repository.addUser(new Student(userId++, 2, "Хренова Гадя Петрович", "gadya", "gadya", 24));
        Repository.addUser(new Student(userId++, 3, "Зварыч Чарльз Вадимович", "char", "char", 30));
        Repository.addUser(new Student(userId++, 1, "Горобчук Элина Сергеевна", "elina", "elina", 31));
        Repository.addUser(new Student(userId++, 2, "Кошелева Ярослава Станиславовна", "slava", "slava", 19));
        Repository.addUser(new Student(userId++, 3, "Гаврилов Шамиль Богданович", "sham", "sham", 22));

        Repository.addUser(new Teacher(userId++, "Агафонова Кармелитта Филатовна", "karm", "karm", 40));
        Repository.addUser(new Teacher(userId++, "Титова Раиса Игоревна", "raisa", "raisa", 42));
        Repository.addUser(new Teacher(userId++, "Фадеев Флор Антонович", "flor", "flor", 51));

        Repository.addUser(new Admin(userId++, "Admin", "root", "root", 27));
    }

    private static void addTests() {
        Random random = new Random();
        UserController userController = new UserController();

        // Adding tests
        int testId = 0;
        for (User user : Repository.getUsers().values()) {
            if (user instanceof Teacher) {
                for (int i = 0; i < random.nextInt(55) + 1; i++) {
                    Repository.addTest(new Test(
                            testId,
                            "Test " + testId,
                            (Teacher) Repository.getUser(user.getId()),
                            8
                    ));
                    testId++;
                }
            }
        }

        // Adding questions to tests
        int questionId = 0;
        for (Test test : Repository.getTests()) {
            for (int j = 0; j < 10; j++) {
                Repository.addQuestion(new Question(questionId++, test.getId(), "Question " + j));
            }
        }

        // Adding answers to question
        int answerId = 0;
        for (Question question : Repository.getQuestions()) {
            for (int i = 0; i < 5; i++) {
                Repository.addAnswer(new Answer(
                        answerId++,
                        question.getId(),
                        "Answer " + i, random.nextBoolean()
                ));
            }
        }

        //Adding a test assignment
        for (User user : Repository.getUsers().values()) {
            if (user instanceof Student) {
                for (int i = 0; i < random.nextInt(35) + 5; i++) {
                    Assignment assignment = new Assignment(
                            user.getId(),
                            random.nextInt(Repository.getTests().size()) + 1,
                            null
                    );
                    Repository.addAssignment(assignment);
                    userController.calculateStudentRating(user.getId()); // Calculating student rating
                }
            }
        }
    }
}

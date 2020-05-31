package com.jp.trc.testing;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.Admin;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.view.*;

import java.util.Random;

/**
 * Test class.
 * Here are created users and tests.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class TestClass {

    public static void main(String[] args) {
        addUsers();
        addTests();
        new UILauncher(new InputValidator(new ConsoleInput())).init();
    }

    private static void addUsers() {
        Repository.addUser(new Student(1, "Тестов Тест Тестович", "test", "test", 30));
        Repository.addUser(new Student(2, "Иванов Иван Иванович", "ivan", "ivan", 25));
        Repository.addUser(new Student(3, "Петров Петр Петрович", "petr", "petr", 18));
        Repository.addUser(new Student(4, "Кузьмина Алевтина Кимовна", "alev", "alev", 20));
        Repository.addUser(new Student(5, "Родионова Габи Иринеевна", "gabi", "gabi", 35));
        Repository.addUser(new Student(6, "Сидоров Сидр Сидорович", "sidr", "sidr", 21));

        Repository.addUser(new Teacher(7, "Агафонова Кармелитта Филатовна", "karm", "karm", 40));
        Repository.addUser(new Teacher(8, "Титова Раиса Игоревна", "raisa", "raisa", 42));
        Repository.addUser(new Teacher(9, "Фадеев Флор Антонович", "flor", "flor", 51));

        Repository.addUser(new Admin(10, "Admin", "root", "root", 27));
    }

    private static void addTests() {
        Repository.addTest(new Test(1, "Testing 1", (Teacher) Repository.getUser(7)));
        Repository.addTest(new Test(2, "Testing 2", (Teacher) Repository.getUser(9)));
        Repository.addTest(new Test(3, "Testing 3", (Teacher) Repository.getUser(8)));
        Repository.addTest(new Test(4, "Testing 4", (Teacher) Repository.getUser(9)));

        // Adding questions to tests
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Repository.addQuestion(new Question(j, i, "Question " + i));
            }
        }

        // Adding answers to question
        int id = 1;
        Random random = new Random();
        for (Question question : Repository.getQuestions()) {
            for (int i = 0; i < 5; i++) {
                Repository.addAnswer(new Answer(id, question.getId(), "Answer " + i, random.nextBoolean()));
                id++;
            }
        }

        Repository.addAssignment(new Assignment(1, 1, 5));
        Repository.addAssignment(new Assignment(3, 2, 3));
        Repository.addAssignment(new Assignment(6, 2, 6));
        Repository.addAssignment(new Assignment(2, 1, 5));
        Repository.addAssignment(new Assignment(3, 3, 1));
        Repository.addAssignment(new Assignment(4, 3, 10));
        Repository.addAssignment(new Assignment(5, 2, 7));
        Repository.addAssignment(new Assignment(5, 4, 8));
    }
}

package com.jp.trc.testing;

import com.jp.trc.testing.model.Institute;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.Admin;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class.
 * Here are created users and tests.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class TestClass {
    /**
     * The institution where users and tests are stored.
     */
    private static Institute institute = new Institute();

    public static void main(String[] args) {
        addUsers();
        addTests();
        new UILauncher(new InputValidator(new ConsoleInput()), institute).init();
    }

    private static void addUsers() {
        institute.addUser(new Student(1, "Тестов Тест Тестович", "test", "test", 30));
        institute.addUser(new Student(2, "Иванов Иван Иванович", "ivan", "ivan", 25));
        institute.addUser(new Student(3, "Петров Петр Петрович", "petr", "petr", 18));
        institute.addUser(new Student(4, "Кузьмина Алевтина Кимовна", "alev", "alev", 20));
        institute.addUser(new Student(5, "Родионова Габи Иринеевна", "gabi", "gabi", 35));
        institute.addUser(new Student(6, "Сидоров Сидр Сидорович", "sidr", "sidr", 21));

        institute.addUser(new Teacher(7, "Агафонова Кармелитта Филатовна", "karm", "karm", 40));
        institute.addUser(new Teacher(8, "Титова Раиса Игоревна", "raisa", "raisa", 42));
        institute.addUser(new Teacher(9, "Фадеев Флор Антонович", "flor", "flor", 51));

        institute.addUser(new Admin(10, "Admin", "root", "root", 27));
    }

    private static void addTests() {
        List<Question> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        answers.add("Ответ 1");
        answers.add("Ответ 2");
        answers.add("Ответ 3");
        answers.add("Ответ 4");
        questions.add(new Question("Вопрос 1", answers, 3));
        questions.add(new Question("Вопрос 2", answers, 1));
        questions.add(new Question("Вопрос 3", answers, 2));
        questions.add(new Question("Вопрос 4", answers, 4));
        questions.add(new Question("Вопрос 5", answers, 3));
        institute.addTest(new Test(1, "Testing 1", questions, (Teacher) institute.getUser(7)));
        institute.addTest(new Test(2, "Testing 2", questions, (Teacher) institute.getUser(9)));
        institute.addTest(new Test(3, "Testing 3", questions, (Teacher) institute.getUser(8)));
        institute.addTest(new Test(4, "Testing 4", questions, (Teacher) institute.getUser(9)));

        institute.addAssignment(new Assignment(1, 1, 5));
        institute.addAssignment(new Assignment(3, 2, 3));
        institute.addAssignment(new Assignment(6, 2, 6));
        institute.addAssignment(new Assignment(2, 1, 5));
        institute.addAssignment(new Assignment(3, 3, 1));
        institute.addAssignment(new Assignment(4, 3, 10));
        institute.addAssignment(new Assignment(5, 2, 7));
        institute.addAssignment(new Assignment(5, 4, 8));
    }
}

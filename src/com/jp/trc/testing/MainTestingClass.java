package com.jp.trc.testing;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.testing.Question;
import com.jp.trc.testing.model.testing.Assignment;
import com.jp.trc.testing.model.testing.Test;
import com.jp.trc.testing.model.users.Admin;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class MainTestingClass {
    private static TrainingCenter center = new TrainingCenter();

    public static void main(String[] args) {
        addUsers();
        addTests();
        new StartUI(new ValidateInput(new ConsoleInput()), center).init();
    }

    private static void addUsers() {
        center.addUser(new Student(1, "Тестов Тест Тестович", "test", "test", 30));
        center.addUser(new Student(2, "Иванов Иван Иванович", "ivan", "ivan", 25));
        center.addUser(new Student(3, "Петров Петр Петрович", "petr", "petr", 18));
        center.addUser(new Student(4, "Кузьмина Алевтина Кимовна", "alev", "alev", 20));
        center.addUser(new Student(5, "Родионова Габи Иринеевна", "gabi", "gabi", 35));
        center.addUser(new Student(6, "Сидоров Сидр Сидорович", "sidr", "sidr", 21));

        center.addUser(new Teacher(7, "Агафонова Кармелитта Филатовна", "karm", "karm", 40));
        center.addUser(new Teacher(8, "Титова Раиса Игоревна", "raisa", "raisa", 42));
        center.addUser(new Teacher(9, "Фадеев Флор Антонович", "flor", "flor", 51));

        center.addUser(new Admin(10, "Admin", "root", "root", 27));
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
        center.addTest(new Test(1, "Testing 1", questions, (Teacher) center.getUser(7)));
        center.addTest(new Test(2, "Testing 2", questions, (Teacher) center.getUser(9)));
        center.addTest(new Test(3, "Testing 3", questions, (Teacher) center.getUser(8)));
        center.addTest(new Test(4, "Testing 4", questions, (Teacher) center.getUser(9)));

        center.addSubscription(new Assignment(1, 1, 5));
        center.addSubscription(new Assignment(3, 2, 3));
        center.addSubscription(new Assignment(6, 2, 6));
        center.addSubscription(new Assignment(2, 1, 5));
        center.addSubscription(new Assignment(3, 3, 1));
        center.addSubscription(new Assignment(4, 3, 10));
        center.addSubscription(new Assignment(5, 2, 7));
        center.addSubscription(new Assignment(5, 4, 8));
    }
}

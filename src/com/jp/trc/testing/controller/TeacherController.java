package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.testing.Subscription;
import com.jp.trc.testing.model.testing.Testing;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherController {
    public static class ViewYourTests implements UserAction {
        @Override
        public void execute(Input input, TrainingCenter center, User user) {
            List<Testing> yourTests = new ArrayList<>();
            yourTests = center.getTests().stream().filter(t -> t.getAuthor().equals(user))
                                                  .collect(Collectors.toList());
            System.out.printf("\t%-8s\t|\t\t%-16s\n", "Наименование теста", "Автор");
            yourTests.forEach(System.out::println);
        }
    }

    public static class ViewTestsResult implements UserAction {
        @Override
        public void execute(Input input, TrainingCenter center, User user) {
            List<Subscription> subscriptions = new ArrayList<>();
            for (Testing test : center.getTests()) {
                if (test.getAuthor().equals(user)) {
                    for (Subscription subscription : center.getSubscriptions()) {
                        if (subscription.getTestId() == test.getId()) {
                            subscriptions.add(subscription);
                        }
                    }
                }
            }
            System.out.printf("\t\t\t%-28s\t|\t%-20s\t|\t%s\n", "ФИО студена", "Наименование теста", "Резултат теста");
            subscriptions.forEach(s -> System.out.printf("%-40s\t|\t%-20s\t|\t%s\n",
                    center.getUser(s.getStudentId()).getName(),
                    center.getTest(s.getTestId()).getTitle(),
                    s.getResult()));
        }
    }
}

package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.tests.*;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;
import com.jp.trc.testing.view.menu.Filter;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Export storage to one file by serializing data from all storage “tables”.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 17.09.2020 10:15
 */
public class DataExportAction implements UserAction {

    private UserController userController = new UserController();
    private TestController testController = new TestController();
    private Filter filter = new Filter(0, 0, Comparator.naturalOrder());

    /**
     * Action when selecting a menu item.
     *
     * @param user The user of this institution for whom the action is performed.
     * @param page Page number to display.
     */
    @Override
    public void execute(User user, int page) {
        String nameFileForExport;
        try {
            nameFileForExport = enterNameFile();
        } catch (ObjectNotFoundException onfe) {
            System.out.println(onfe.getMessage());
            nameFileForExport = enterNameFile();
        }


        File fileForExport = new File("resources/" + nameFileForExport + ".ser");

        try {
            fileForExport.createNewFile();
            ObjectOutputStream objectOutputStream = openOutputStream(fileForExport);

            exportUsers(objectOutputStream);
            exportGroups(objectOutputStream);
            exportTests(objectOutputStream);
            exportQuestions(objectOutputStream);
            exportAnswers(objectOutputStream);
            exportAssignments(objectOutputStream);
            exportAnswerToQuestions(objectOutputStream);

            closeOutputStream(objectOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportAnswerToQuestions(ObjectOutputStream objectOutputStream) throws IOException {
        List<AnswerToQuestion> answerToQuestionList = new ArrayList<>(
                testController.getAnswerToQuestions()
        );
        for (AnswerToQuestion answer : answerToQuestionList)
            objectOutputStream.writeObject(answer);
    }

        private void exportAssignments(ObjectOutputStream objectOutputStream) throws IOException {
        List<Assignment> assignments = userController.getAssignments();
        for (Assignment assignment : assignments)
            objectOutputStream.writeObject(assignment);
    }

    private void exportAnswers(ObjectOutputStream objectOutputStream) throws IOException {
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();

        testController.getTests().stream()
                .forEach(test -> questions.addAll(testController.getTestQuestions(test.getId())));

        questions.stream()
                .forEach(question -> answers.addAll(testController.getAnswerVariants(question.getId())));

        for (Answer answer : answers)
            objectOutputStream.writeObject(answer);
    }

    private void exportQuestions(ObjectOutputStream objectOutputStream) throws IOException {
        List<Question> questions = new ArrayList<>();
        testController.getTests().stream()
                .forEach(test -> questions.addAll(testController.getTestQuestions(test.getId())));
        for (Question question : questions)
            objectOutputStream.writeObject(question);
    }

    private void exportTests(ObjectOutputStream objectOutputStream) throws IOException {
        List<Test> tests = testController.getTests();
        for (Test test : tests)
            objectOutputStream.writeObject(test);
    }

    private void exportGroups(ObjectOutputStream objectOutputStream) throws IOException {
        List<Group> groups = userController.getGroups(filter);
        for (Group group : groups)
            objectOutputStream.writeObject(group);
    }

    private void exportUsers(ObjectOutputStream objectOutputStream) throws IOException {
        List<User> users = userController.getAllUsers(filter);
        for (User usr : users)
            objectOutputStream.writeObject(usr);
    }

    private ObjectOutputStream openOutputStream(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        return objectOutputStream;
    }

    private void closeOutputStream(ObjectOutputStream objectOutputStream) throws IOException {
        if (objectOutputStream != null) {
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }

    private String enterNameFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя файла для экспорта данных:");
        String nameFileForExport = scanner.nextLine();
        if (nameFileForExport.length() == 0) {
            throw new ObjectNotFoundException("The file name cannot be empty!");
        }
        return nameFileForExport;
    }
}

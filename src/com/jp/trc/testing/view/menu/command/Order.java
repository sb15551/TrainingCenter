package com.jp.trc.testing.view.menu.command;

import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.Admin;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.MenuOutException;
import com.jp.trc.testing.view.menu.SubMenu;

import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 24.08.2020 9:13
 */
public class Order implements Command {

    /**
     * List submenu items.
     */
    private SubMenu subMenu;

    /**
     * Sorting direction.
     */
    private String direction;

    /**
     * The field by which the sorting will be performed.
     */
    private String fieldName;

    /**
     * Comparators for students.
     */
    private HashMap<String, Comparator> fieldsNameForStudent = new HashMap<>();

    /**
     * Comparators for teachers.
     */
    private HashMap<String, Comparator> fieldsNameForTeacher = new HashMap<>();

    /**
     * Comparators for admin.
     */
    private HashMap<String, Comparator> fieldsName = new HashMap<>();

    /**
     * Sort by student name.
     */
    private Comparator<User> nameStudent =
            (User o1, User o2) -> o1.getName().compareTo(o2.getName());

    /**
     * Sort by student age.
     */
    private Comparator<User> ageStudent =
            (User o1, User o2) -> Integer.compare(o1.getAge(), o2.getAge());

    /**
     * Sort by student rating.
     */
    private Comparator<? extends User> ratingStudent =
            (User o1, User o2) -> Double.compare(((Student) o1).getRating(), ((Student) o2).getRating());

    /**
     * Sort by title test.
     */
    private Comparator<Test> titleTest =
            (Test o1, Test o2) -> o1.getTitle().compareTo(o2.getTitle());

    /**
     * Sort by author test.
     */
    private Comparator<Test> authorTest =
            (Test o1, Test o2) -> o1.getAuthor().compareTo(o2.getAuthor());

    /**
     * Sort by user name.
     */
    private Comparator<User> nameUser =
            (User o1, User o2) -> o1.getName().compareTo(o2.getName());

    /**
     * Sort by user age.
     */
    private Comparator<User> ageUser =
            (User o1, User o2) -> Integer.compare(o1.getAge(), o2.getAge());

    /**
     * Constructor for creating a order.
     * @param subMenu List submenu items.
     * @param key Menu item.
     */
    public Order(SubMenu subMenu, String key) {
        this.subMenu = subMenu;
        this.direction = key.substring(0, 1);
        this.fieldName = key.substring(1);
        initComparators();
    }

    /**
     * Executes the command entered from the keyboard.
     */
    @Override
    public void execute() {
        existField();

        if (direction.matches("\\s*\\+\\s*")) {
            subMenu.getFilter().setComparator(getComparator(fieldName));
            subMenu.show(1);
        }
        if (direction.matches("\\s*\\-\\s*")) {
            subMenu.getFilter().setComparator(getComparator(fieldName).reversed());
            subMenu.show(1);
        }
    }

    private void existField() {
        if (subMenu.getUser() instanceof Student) {
            if (!fieldsNameForStudent.containsKey(fieldName)) {
                throw new MenuOutException("Нет такого поля!");
            }
        }
        if (subMenu.getUser() instanceof Teacher) {
            if (!fieldsNameForTeacher.containsKey(fieldName)) {
                throw new MenuOutException("Нет такого поля!");
            }
        }
        if (subMenu.getUser() instanceof Admin) {
            if (!fieldsName.containsKey(fieldName)) {
                throw new MenuOutException("Нет такого поля!");
            }
        }
    }

    private <T extends Comparable<? super T>> Comparator<? super T> getComparator(String fieldName) {
        if (subMenu.getUser() instanceof Student) {
            return fieldsNameForStudent.get(fieldName);
        }
        if (subMenu.getUser() instanceof Teacher) {
            return fieldsNameForTeacher.get(fieldName);
        }
        return fieldsName.get(fieldName);
    }

    private void initComparators() {
        fieldsNameForTeacher.put("name", nameStudent);
        fieldsNameForTeacher.put("age", ageStudent);
        fieldsNameForTeacher.put("rating", ratingStudent);
        fieldsNameForStudent.put("title", titleTest);
        fieldsNameForStudent.put("author", authorTest);
        fieldsName.put("name", nameStudent);
        fieldsName.put("age", ageStudent);
    }

    /**
     * Printing the sorting menu for the user.
     * @param user User for whom you need to print the menu.
     */
    public static void printMenuOrder(User user) {
        if (user instanceof Student) {
            System.out.println(
                    "\tДля сортировки введите:\n"
                            + "\t\"+title\" или \"-title\" чтобы отсортировать по наименованию теста\n"
                            + "\t\"+author\" или \"-author\" чтобы отсортировать по автору теста"
            );
        }
        if (user instanceof Teacher) {
            System.out.println(
                    "\tДля сортировки введите:\n"
                            + "\t\"+name\" или \"-name\" чтобы отсортировать по имени студента\n"
                            + "\t\"+age\" или \"-age\" чтобы отсортировать по возрасту студента\n"
                            + "\t\"+rating\" или \"-rating\" чтобы отсортировать по рейтингу студента"
            );
        }
        if (user instanceof Admin) {
            System.out.println(
                    "\tДля сортировки введите:\n"
                            + "\t\"+name\" или \"-name\" чтобы отсортировать по имени пользователя\n"
                            + "\t\"+age\" или \"-age\" чтобы отсортировать по возрасту пользователя\n"
            );
        }
    }
}

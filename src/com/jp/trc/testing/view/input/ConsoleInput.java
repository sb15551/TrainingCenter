package com.jp.trc.testing.view.input;

import com.jp.trc.testing.view.exception.MenuOutException;

import java.util.List;
import java.util.Scanner;

/**
 * Class for entering menu items.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Ask for keyboard input.
     * @param question Keyboard input hint.
     * @return String entered from the keyboard.
     */
    @Override
    public String askItemMenu(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    /**
     * Validates keyboard input.
     * @param question Keyboard input hint.
     * @param range List of menu item numbers.
     * @return Menu item number.
     */
    @Override
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.askItemMenu(question));
        boolean exist = false;
        if (range.contains(key)) {
            exist = true;
            return key;
        }
        if (!exist) {
            throw new MenuOutException("Вне диапазона меню");
        }
        return key;
    }

    /**
     * Validates keyboard input for subMenu.
     * @param question Keyboard input hint.
     * @param sizeMenu Size menu.
     * @param amountPage Amount page for subMenu.
     * @return Menu item value.
     * @throws MenuOutException Thrown out if the entered value does not fall into the menu dimension.
     * @throws NumberFormatException Thrown out if the entered value is not a number.
     */
    @Override
    public String askSubMenu(String question, int sizeMenu, int amountPage)
            throws MenuOutException, NumberFormatException {

        String item = this.askItemMenu(question).trim();

        if (item.matches("\\d+")) {
            int key = Integer.parseInt(item);
            if (key < sizeMenu && key >= 0) {
                return item;
            } else {
                throw new MenuOutException("Вне диапазона меню");
            }
        } else if (item.matches("^p\\s*\\d+")) {
            int key = Integer.parseInt(item.replaceAll("p", ""));
            if (key <= amountPage && key >= 0) {
                return item;
            } else {
                throw new MenuOutException("Такой страницы не существует");
            }
        } else {
            throw new NumberFormatException("Введите корректные данные");
        }
    }

    /**
     * Entering the response number when passing the test.
     * @param question Keyboard input hint.
     * @param size Size menu.
     * @return Menu item numbers.
     */
    @Override
    public int[] askNumberAnswer(String question, int size) {
        String[] answers = this.askItemMenu(question).trim().split("\\s+");
        int[] keys = new int[answers.length];

        for (int i = 0; i < answers.length; i++) {
            int key = Integer.parseInt(answers[i]);
            if (key <= size && key > 0) {
                keys[i] = key;
            } else {
                throw new MenuOutException("Вне диапазона меню");
            }
        }

        return keys;
    }
}

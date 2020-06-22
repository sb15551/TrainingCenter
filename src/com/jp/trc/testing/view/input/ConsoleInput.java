package com.jp.trc.testing.view.input;

import com.jp.trc.testing.view.menu.MenuOutException;

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
     * Entering the response number when passing the test.
     * @param question Keyboard input hint.
     * @param size Size menu.
     * @return
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
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
}

package com.jp.trc.testing.view;

/**
 * Exception is thrown if enter a menu item that does not exist.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 27.05.2020 12:07
 */
public class MenuOutException extends RuntimeException {
    public MenuOutException(String msg) {
        super(msg);
    }
}

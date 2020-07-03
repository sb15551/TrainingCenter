package com.jp.trc.testing.view.exception;

/**
 * Exception is thrown if sought-for object not found.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 03.07.2020 8:27
 */
public class ObjectNotFoundException extends RuntimeException {

    /**
     * Constructor for creating an exception
     * @param msg Message to display.
     */
    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}

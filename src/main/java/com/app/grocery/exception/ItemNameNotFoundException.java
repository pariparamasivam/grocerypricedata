package com.app.grocery.exception;

@SuppressWarnings("serial")
public class ItemNameNotFoundException extends RuntimeException {
    public ItemNameNotFoundException(final String message) {
        super(message);
    }

}

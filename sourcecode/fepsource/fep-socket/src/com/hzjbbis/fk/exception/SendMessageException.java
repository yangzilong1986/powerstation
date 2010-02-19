package com.hzjbbis.fk.exception;

import java.io.IOException;

public class SendMessageException extends RuntimeException {
    private static final long serialVersionUID = -7790752754588806327L;

    public SendMessageException(IOException e) {
        super(e);
    }

    public SendMessageException(String message) {
        super(message);
    }
}
package com.elminster.common.command;

public class CommandExecException extends RuntimeException {

    public CommandExecException() {
    }

    public CommandExecException(String message) {
        super(message);
    }

    public CommandExecException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandExecException(Throwable cause) {
        super(cause);
    }
}

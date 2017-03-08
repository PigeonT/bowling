package de.pigeont.bowlinggame.exception;

public final class ExecuteGameException extends Exception {
    public ExecuteGameException() {
    }

    public ExecuteGameException(String message) {
        super(message);
    }

    public ExecuteGameException(Throwable cause) {
        super(cause);
    }

    public ExecuteGameException(String message, Throwable cause) {
        super(message, cause);
    }
}

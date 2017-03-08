package de.pigeont.bowlinggame.exception;

public final class InitGameException extends Exception {
    public InitGameException() {
    }

    public InitGameException(String message) {
        super(message);
    }

    public InitGameException(Throwable cause) {
        super(cause);
    }

    public InitGameException(String message, Throwable cause) {
        super(message, cause);
    }
}

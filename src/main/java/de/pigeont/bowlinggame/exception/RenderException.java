package de.pigeont.bowlinggame.exception;

public final class RenderException extends Exception {
    public RenderException() {
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }
}

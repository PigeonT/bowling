package de.pigeont.bowlinggame.exception;

@Deprecated
public final class RenderException extends BowlingException {
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

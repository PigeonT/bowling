package de.pigeont.bowlinggame.config;

import java.util.logging.*;

public final class Configuration {

    private static final String LOGGER = "BolwingGame";
    private static Handler consoleHandler = new BowlingconsoleHandler();
    public static final Logger logger = Logger.getLogger(LOGGER);

    private Configuration() {
    }

    public static void init() {
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINE);
    }

    private static final class BowlingconsoleHandler extends Handler {
        @Override
        public void publish(LogRecord record) {
            if (getFormatter() == null) {
                setFormatter(new SimpleFormatter());
            }

            try {
                String message = getFormatter().format(record);
                if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                    System.err.write(message.getBytes());
                } else {
                    System.out.write(message.getBytes());
                }
            } catch (Exception exception) {
                reportError(null, exception, ErrorManager.FORMAT_FAILURE);
            }

        }

        @Override
        public void close() throws SecurityException {
        }

        @Override
        public void flush() {
        }
    }
}

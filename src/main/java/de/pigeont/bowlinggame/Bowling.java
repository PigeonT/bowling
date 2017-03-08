package de.pigeont.bowlinggame;

import de.pigeont.bowlinggame.config.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;


public final class Bowling {
    private Bowling() {
    }


    public static void main(String[] args) {
        Configuration.init();
        Logger logger = Configuration.logger;
        logger.log(Level.INFO, "game started");

        try {
            GameStarter.createNewGame();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception: game terminated unexcepted", e);
            logger.log(Level.FINE, e.getMessage());
            System.exit(ReturnValue.FAILURE.getReturnCode());
        }

    }

    enum ReturnValue {
        SUCCESS(0),
        FAILURE(1);

        private int returnCode;

        private ReturnValue(int returnCode) {
            this.returnCode = returnCode;
        }

        public int getReturnCode() {
            return returnCode;
        }
    }
}
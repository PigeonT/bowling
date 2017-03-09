package de.pigeont.bowlinggame;

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
            createNewGame();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception: game terminated unexcepted", e);
            logger.log(Level.FINE, e.getMessage());
            System.exit(ReturnValue.FAILURE.getReturnCode());
        }
        System.exit(ReturnValue.SUCCESS.getReturnCode());
    }

    private static void createNewGame() {
        //all single object pattern and dependency injection
        GameController gcontroller = GameController.createController(Configuration.logger);
        GameStatusManager gm = GameStatusManager.createGameStatusManager(Configuration.logger);
        GameRender gr = GameRender.createRender(Configuration.logger);

        try {
            gcontroller.setStatusManager(gm);
            gcontroller.setGameRender(gr);
            gm.setGameController(gcontroller);
        } catch (ReassignmentException e) {
            Configuration.logger.log(Level.WARNING,
                    "reassignment of bowling component, all the component is immutable", e);
        }
        gr.setController(gcontroller);

        gcontroller.startNewGame();
    }

    enum ReturnValue {
        SUCCESS(0),
        FAILURE(1);

        private int returnCode;

        ReturnValue(int returnCode) {
            this.returnCode = returnCode;
        }

        public int getReturnCode() {
            return returnCode;
        }
    }
}
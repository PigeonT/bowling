package de.pigeont.bowlinggame;

import de.pigeont.bowlinggame.config.Configuration;
import de.pigeont.bowlinggame.controller.GameController;
import de.pigeont.bowlinggame.model.GameManager;
import de.pigeont.bowlinggame.render.GameRender;

import java.util.logging.Level;
import java.util.logging.Logger;


public final class Bowling {
    private Bowling() {
    }

    public static void main(String[] args) {
        Configuration.init();
        Logger logger = Configuration.logger;
        logger.log(Level.INFO, "game starts");

        try {
            createNewGame();
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Exception: game terminated unexcepted", e);
            System.exit(ReturnValueEnum.FAILURE.getReturnCode());
        }
        logger.log(Level.INFO, "game ends");
        System.exit(ReturnValueEnum.SUCCESS.getReturnCode());
    }

    private static void createNewGame() {
        //all single object pattern and dependency injection
        GameController gcontroller = GameController.createController(Configuration.logger);
        GameManager gm = GameManager.createGameStatusManager();
        GameRender gr = GameRender.createRender();

        gcontroller.setStatusManager(gm);
        gcontroller.setGameRender(gr);
        gm.setGameController(gcontroller);
        gr.setController(gcontroller);

        gcontroller.startNewGame();
    }

    enum ReturnValueEnum {
        SUCCESS(0),
        FAILURE(1);

        private int returnCode;

        ReturnValueEnum(int returnCode) {
            this.returnCode = returnCode;
        }

        public int getReturnCode() {
            return returnCode;
        }
    }
}
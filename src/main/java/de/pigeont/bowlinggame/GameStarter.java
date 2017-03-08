package de.pigeont.bowlinggame;

import de.pigeont.bowlinggame.config.Configuration;
import de.pigeont.bowlinggame.controller.GameController;
import de.pigeont.bowlinggame.gameobjects.GameStatusManager;
import de.pigeont.bowlinggame.render.GameRender;

final class GameStarter {
    private GameStarter() {
    }

    static void createNewGame() {
        //all single object pattern and dependency injection
        GameController gcontroller = GameController.createController(Configuration.logger);
        GameStatusManager gm = GameStatusManager.createGameStatusManager(Configuration.logger);
        GameRender gr = GameRender.createRender(Configuration.logger);

        gcontroller.setStatusManager(gm);
        gcontroller.setGameRender(gr);

        gm.setGameController(gcontroller);
        gr.setGameController(gcontroller);

        gcontroller.startNewGame();
    }

}

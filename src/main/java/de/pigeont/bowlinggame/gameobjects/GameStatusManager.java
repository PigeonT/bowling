package de.pigeont.bowlinggame.gameobjects;

import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.controller.GameController;

import java.util.logging.Logger;

public final class GameStatusManager {
    private final Logger logger;
    private static GameStatusManager gameManager;
    private GameController gameController;
    private Long gameControllerID = 0L;

    private GameStatusManager(Logger l) {
        logger = l;
    }

    @NotNull
    public static GameStatusManager createGameStatusManager(@NotNull Logger lc) {
        if (gameManager != null) return gameManager;
        gameManager = new GameStatusManager(lc);
        return gameManager;
    }

    public void setGameController(@NotNull GameController gameController) {
        if (gameControllerID != 0L) throw new RuntimeException("reassignment of render of game constrolelr");
        this.gameController = gameController;
        gameControllerID = 1L;
    }
}

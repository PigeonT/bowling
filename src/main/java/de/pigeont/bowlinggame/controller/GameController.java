package de.pigeont.bowlinggame.controller;

import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.exception.ExecuteGameException;
import de.pigeont.bowlinggame.exception.InitGameException;
import de.pigeont.bowlinggame.exception.RenderException;
import de.pigeont.bowlinggame.gameobjects.GameStatusManager;
import de.pigeont.bowlinggame.render.GameRender;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    private static GameController controller;
    private Logger logger;
    private Boolean stop;
    private GameStatusManager statusManager;
    private Long statusManagerID = 0L;
    private GameRender render;
    private Long renderID = 0L;

    private GameController(Logger l) {
        logger = l;
        stop = false;
    }

    public void startNewGame() {

        try {
            init();
            run();
            shutdown();
        } catch (InitGameException e) {
            logger.log(Level.SEVERE, "initialize game failed", e);
            throw new RuntimeException(e);
        } catch (ExecuteGameException e) {
            logger.log(Level.SEVERE, "executing game interrupted", e);
            throw new RuntimeException(e);
        }

    }

    private void init() throws InitGameException {
        try {
            render.initGame();
        } catch (RenderException e) {
            throw new InitGameException(e);
        }
    }

    private void run() throws ExecuteGameException {

        do {
            try {
                processInput();
                updateGameStatus();
                render.update();
            } catch (RenderException e) {
                logger.log(Level.SEVERE, "executing game interrupted", e);
                throw new ExecuteGameException(e);
            }
        } while (!stop);


    }

    private void processInput() throws ExecuteGameException {

    }

    private void updateGameStatus() throws ExecuteGameException {
        try {
            render.update();
        } catch (RenderException e) {
            logger.log(Level.SEVERE, "executing game interrupted", e);
            throw new ExecuteGameException(e);
        }
    }

    private void shutdown() {

    }

    @NotNull
    public static GameController createController(@NotNull Logger l) {
        controller = controller == null ? new GameController(l) : controller;
        return controller;
    }

    public void setStatusManager(@NotNull GameStatusManager sm) {
        if (statusManagerID != 0L) throw new RuntimeException("reassignment of statusmanager of game constrolelr");
        this.statusManager = sm;
        statusManagerID = 1L;
    }

    public void setGameRender(@NotNull GameRender gameRender) {
        if (renderID != 0L) throw new RuntimeException("reassignment of render of game constrolelr");
        this.render = gameRender;
        renderID = 1L;
    }
}

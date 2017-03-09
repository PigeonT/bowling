package de.pigeont.bowlinggame;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    private static final Integer COL = 96;
    private static final Integer ROW = 24;
    private static GameController controller;
    private final Logger logger;
    private Terminal terminal;
    private Screen screen;
    private GameStatusManager manager;
    private GameRender render;
    private Boolean stop;
    private Inputprocessor inputprocessor;
    private Long statusManagerID = 0L;
    private Long renderID = 0L;

    private GameController(Logger l) {
        logger = l;
        stop = false;
        inputprocessor = new Inputprocessor();

    }

    @NotNull
    public static GameController createController(@NotNull Logger l) {
        controller = null == controller ? new GameController(l) : controller;
        return controller;
    }

    public void startNewGame() {

        try {
            init();
        } finally {
            if (null != screen) {
                try {
                    screen.close();
                } catch (IOException e) {
                    logger.log(Level.FINE, "IOexecution process exception", e);
                }
            }

            if (null != screen) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    logger.log(Level.FINE, "IOexecution process exception", e);
                }
            }
        }

    }

    private void init() {
        try {
            terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(COL, ROW))
                    .createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            logger.log(Level.INFO, "start screen successfully");
            screen.clear();

            manager.init();
            render.initGame();

            run();

            screen.stopScreen();
            logger.log(Level.INFO, "stop screen successfully");


        } catch (IOException e) {
            logger.log(Level.SEVERE, "Render process terminated", e);
            logger.log(Level.FINE, e.getMessage());
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "close terminal", e);
            try {
                screen.stopScreen();
            } catch (IOException e1) {
                logger.log(Level.INFO, "stop screen unexpectedly");
            }
        }
    }

    private void run() {

        do {
            try {
                //60fps
                Thread.sleep(17);
            } catch (InterruptedException e) {
                logger.log(Level.FINE, e.getMessage());
                throw new RuntimeException(e);
            }
            processInput();
            updateGameStatus();
            render.update();
        } while (!stop);

    }

    private void processInput() {
        inputprocessor.processInput();
    }

    private void updateGameStatus() {
        manager.update();
    }

    public Screen getScreen() {
        return screen;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setStatusManager(@NotNull GameStatusManager sm) {
        if (0L != statusManagerID) throw new RuntimeException("reassignment of statusmanager of game constrolelr");
        this.manager = sm;
        statusManagerID = 1L;
    }

    public void setGameRender(@NotNull GameRender gameRender) {
        if (0L != renderID) throw new RuntimeException("reassignment of render of game constrolelr");
        this.render = gameRender;
        renderID = 1L;
    }

    public List<Integer[]> getOriginPins() {
        return manager.getOriginPins();
    }

    public boolean powerIsSet() {
        return manager.powerIsSet();
    }

    public boolean hookIsSet() {
        return manager.hookIsSet();
    }

    private class Inputprocessor {
        private Character input;

        private Inputprocessor() {
        }

        private void processInput() {

            KeyStroke key;
            try {
                key = GameController.this.screen.pollInput();
                if (null == key) return;
                input = key.getCharacter();
                switch (input) {
                    case 'q':
                        stop = true;
                        logger.log(Level.INFO, "use enter q to stop game");
                        break;
                    case '\n':
                        if (!manager.powerIsSet()) {
                            manager.setPower(render.getProgressbar());
                            render.resetProgressbar();
                            break;
                        }
                        if (!manager.hookIsSet()) {
                            manager.setHook(render.getProgressbar());
                            break;
                        }
                    default:
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                stop = true;
                logger.log(Level.INFO, "close terminal");
            }

        }
    }
}

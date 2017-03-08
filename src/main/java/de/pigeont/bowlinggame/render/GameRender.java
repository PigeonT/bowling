package de.pigeont.bowlinggame.render;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.controller.GameController;
import de.pigeont.bowlinggame.exception.RenderException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GameRender {
    private static GameRender render;
    private final Logger logger;
    private GameController gameController;
    private Long gameControllerID = 0L;

    private Terminal terminal;
    private Screen screen;

    //graphics should be ideally create them and then let then be GC:ed.
    //this because: http://mabe02.github.io/lanterna/apidocs/3.0/index.html?com/googlecode/lanterna/graphics/AbstractTextGraphics.html
    private TextGraphics graphics;

    public GameRender(Logger _logger) {
        logger = _logger;
    }

    @NotNull
    public static GameRender createRender(@NotNull Logger logger) {
        if (render != null) return render;
        render = new GameRender(logger);
        return render;
    }

    public void initGame() throws RenderException {
        try {
            init();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Render process terminated");
            logger.log(Level.FINE, e.getMessage());
            throw new RenderException("Render process terminated unexpected", e);
        }
    }

    private void init() throws IOException {
        terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(96, 24))
                .createTerminal();

        screen = new TerminalScreen(terminal);

        screen.startScreen();
        logger.log(Level.INFO, "start screen");

        screen.clear();

        initDraw();

        screen.stopScreen();
        logger.log(Level.INFO, "stop screen");
    }

    private void initDraw() throws IOException {
        logger.log(Level.FINE, screen.getTerminalSize().toString());

        graphics = screen.newTextGraphics();
        graphics.drawLine(36, 0, 36, 24, '|');

        screen.refresh();

    }

    public void update() throws RenderException {

    }

    public void setGameController(@NotNull GameController gameController) {
        if (gameControllerID != 0L) throw new RuntimeException("reassignment of render of game constrolelr");
        this.gameController = gameController;
        gameControllerID = 1L;
    }
}

package de.pigeont.bowlinggame;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.util.logging.Logger;

public final class GameRender {
    private static final Integer OFFSET = 40;
    private static GameRender render;
    private final Logger logger;
    private GameController controller;
    private Screen screen;
    private Long gameControllerID = 0L;
    //range from 0 to 10 mean 0% to 100%
    private Integer progressbar = OFFSET;
    //indicate progressbar increase or decrease
    private Boolean increaseprogressbar = true;

    public GameRender(Logger _logger) {
        logger = _logger;
    }

    @NotNull
    public static GameRender createRender(@NotNull Logger logger) {
        render = null == render ? render = new GameRender(logger) : render;
        return render;
    }

    public void initGame() {
        try {
            //put it in stack for performance
            screen = controller.getScreen();
            TextGraphics g = screen.newTextGraphics();

            //draw ambient and information
            g.setForegroundColor(TextColor.ANSI.CYAN);
            g.drawLine(36, 0, 36, 24, '|');
            g.putString(46, 1, "Information: ", SGR.BOLD);
            g.putString(46, 2, "use keyboard to play", SGR.BOLD);
            g.putString(46, 3, "'q' : quit", SGR.BOLD);
            g.putString(46, 4, "enter : throw", SGR.BOLD);

            //draw pins
            drawPins(g);
            //draw ball
            drawBall(g);
            //draw power
            drawPower(g);
            //draw hook
            if (controller.powerIsSet()) drawHook(g);

            screen.refresh();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            TextGraphics g = screen.newTextGraphics();

            //draw power
            drawPower(g);

            //draw hook
            if (controller.powerIsSet()) drawHook(g);
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawPower(TextGraphics g) throws IOException {
        g.putString(40, 7, "weak power", SGR.BOLD);
        g.putString(78, 7, "strong power", SGR.BOLD);
        if (controller.powerIsSet()) {
            g.drawRectangle(new TerminalPosition(40, 8), new TerminalSize(49, 3), '#');
            g.setCharacter(43, 9, '+');
            return;
        }
        g.drawRectangle(new TerminalPosition(40, 8), new TerminalSize(49, 3), '#');
        drawProcessBar(g, 9);
    }

    private void drawHook(TextGraphics g) {
        g.putString(40, 11, "left", SGR.BOLD);
        g.putString(78, 11, "right", SGR.BOLD);
        if (controller.hookIsSet()) {
            g.drawRectangle(new TerminalPosition(40, 12), new TerminalSize(49, 3), '#');
            g.setCharacter(43, 13, '+');
            return;
        }
        g.drawRectangle(new TerminalPosition(40, 12), new TerminalSize(49, 3), '#');
        drawProcessBar(g, 13);
    }

    private void drawPins(TextGraphics g) throws IOException {
        g.setForegroundColor(TextColor.ANSI.DEFAULT);
        controller.getOriginPins()
                .parallelStream()
                .forEach(p -> g.setCharacter(p[0], p[1], 'I'));

    }

    private void drawBall(TextGraphics g) throws IOException {
        g.setCharacter(15, 18, '@');

    }

    private void drawProcessBar(TextGraphics g, Integer y) {
        if (increaseprogressbar) {
            if (87 > progressbar) {
                g.setCharacter(progressbar, y, '+');

                progressbar++;
                return;
            }
            increaseprogressbar = false;
            return;
        }
        if (41 < progressbar) {
            g.setCharacter(progressbar, y, ' ');
            progressbar--;
            return;
        }
        increaseprogressbar = true;
    }

    public void setController(@NotNull GameController controller) {
        if (0L != gameControllerID) throw new RuntimeException("reassignment of render of game constrolelr");
        this.controller = controller;
        gameControllerID = 1L;
    }

    public Integer getProgressbar() {
        return progressbar;
    }

    public void resetProgressbar() {
        this.progressbar = OFFSET;
    }
}

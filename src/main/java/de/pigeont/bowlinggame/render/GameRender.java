package de.pigeont.bowlinggame.render;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.controller.GameController;

import java.io.IOException;
import java.util.function.Consumer;

public final class GameRender {
    private static GameRender render;
    private GameController controller;
    private Screen screen;
    private int progressbar = 0;
    private boolean increaseprogressbar = true;

    public GameRender() {
    }

    @NotNull
    public static GameRender createRender() {
        render = render == null ? render = new GameRender() : render;
        return render;
    }

    public void initGame(Screen screen) {
        this.screen = screen;
    }

    public void update() {
        _redraw();
    }

    private void _redraw() {
        //http://mabe02.github.io/lanterna/apidocs/3.0/index.html?com/googlecode/lanterna/graphics/AbstractTextGraphics.html
        //Don't hold on to your TextGraphics objects for too long; ideally create them and let them be GC:ed
        //g location is in stack for performance
        TextGraphics g = screen.newTextGraphics();
        screen.clear();
        //using default double buffering
        drawAmbient(g);
        try {
            drawPins(g);
            drawBall(g);
            drawPower(g);
            if (controller.powerIsSet()) drawHook(g);
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void drawAmbient(TextGraphics g) {
        g.setForegroundColor(TextColor.ANSI.CYAN);
        g.drawLine(10, 2, 10, 24, '|');
        g.drawLine(20, 2, 20, 24, '|');
        g.putString(46, 1, "Information: ", SGR.BOLD);
        g.putString(46, 2, "use keyboard to play", SGR.BOLD);
        g.putString(46, 3, "esc : quit", SGR.BOLD);
        g.putString(46, 4, "enter : throw", SGR.BOLD);
        g.putString(46, 5, "arrow key : control spot", SGR.BOLD);
    }

    private void drawPower(TextGraphics g) throws IOException {
        g.putString(40, 7, "weak power", SGR.BOLD);
        g.putString(78, 7, "strong power", SGR.BOLD);
        g.drawRectangle(new TerminalPosition(40, 8), new TerminalSize(49, 3), '#');
        if (controller.powerIsSet()) {
            int p = controller.getPower();
            g.fillRectangle(new TerminalPosition(41, 9),
                    new TerminalSize(p, 1),
                    '+');
            return;
        }
        drawProcessBar(g, 9);
    }

    private void drawHook(TextGraphics g) {
        g.putString(40, 11, "left", SGR.BOLD);
        g.putString(84, 11, "right", SGR.BOLD);
        g.drawRectangle(new TerminalPosition(40, 12), new TerminalSize(49, 3), '#');
        if (controller.hookIsSet()) {
            int h = controller.getHook();
            g.fillRectangle(new TerminalPosition(41, 13),
                    new TerminalSize(h, 1),
                    '+');
            return;
        }
        drawProcessBar(g, 13);
    }

    private void drawPins(TextGraphics g) throws IOException {
        controller.getPinsPosition()
                .parallelStream()
                .forEach(p -> g.setCharacter(p[0], p[1], 'I'));
    }

    private void drawBall(TextGraphics g) throws IOException {
        int[] p = controller.getBallPosition();
        g.setCharacter(p[0], p[1], '@');
    }

    private void drawProcessBar(final TextGraphics g, int y) {
        Consumer<Void> fill = (f) -> g.fillRectangle(
                new TerminalPosition(41, y),
                new TerminalSize(progressbar, 1),
                '+');
        if (increaseprogressbar) {
            if (47 > progressbar) {
                fill.accept(null);
                progressbar++;
                return;
            }
            increaseprogressbar = false;
        }
        if (1 < progressbar) {
            fill.accept(null);
            progressbar--;
            return;
        }
        increaseprogressbar = true;
    }

    public void setController(@NotNull GameController controller) {
        this.controller = controller;
    }

    public int getProgressbar() {
        return progressbar;
    }

    public void resetProgressbar() {
        this.progressbar = 0;
    }


}

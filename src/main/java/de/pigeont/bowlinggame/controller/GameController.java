package de.pigeont.bowlinggame.controller;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.constants.GlobalConstants;
import de.pigeont.bowlinggame.model.GameManager;
import de.pigeont.bowlinggame.model.items.Hook;
import de.pigeont.bowlinggame.model.sprites.Ball;
import de.pigeont.bowlinggame.model.sprites.Pin;
import de.pigeont.bowlinggame.render.GameRender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

public final class GameController {
    private final int COL = GlobalConstants.COL;
    private final int ROW = GlobalConstants.ROW;
    private final GlobalConstants.DirectionEnum LEFT = GlobalConstants.DirectionEnum.LEFT;
    private final GlobalConstants.DirectionEnum RIGHT = GlobalConstants.DirectionEnum.RIGHT;
    private static GameController controller;
    private final BallController ballController = new BallController();
    private final PinsController pinsController = new PinsController();
    private final CollisionController collisionController = new CollisionController();
    private final RoundController roundController = new RoundController(this);
    private Inputprocessor inputprocessor = new Inputprocessor();
    private Terminal terminal;
    private Screen screen;
    private GameManager manager;
    private GameRender render;
    private boolean stop = false;
    private boolean gutterBall;

    private GameController(Logger l) {
    }

    @NotNull
    public static GameController createController(@NotNull Logger l) {
        controller = controller == null ? new GameController(l) : controller;
        return controller;
    }

    public void startNewGame() {

        try {
            init();
        } finally {
            if (null != screen) {
                try {
                    screen.close();
                } catch (IOException ignore) {

                }
            }

            if (null != screen) {
                try {
                    terminal.close();
                } catch (IOException ignore) {

                }
            }
        }

    }

    public boolean isGutterBall() {
        return manager.getBall().gutterBall();
    }

    private void init() {

        try {
            terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(COL, ROW))
                    .createTerminal();
            terminal.bell();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.clear();
            render.initGame(screen);
            run();
            screen.stopScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void run() {

        do {
            try {
                Thread.sleep(35);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            processInput();
            updateGameStatus();
            render.update();
        } while (!stop);

    }

    private void processInput() {
        if (isWaitingInput()) inputprocessor.processInput();
    }

    private void updateGameStatus() {
        if (!isWaitingInput()) {
            ballController.update();
            pinsController.update();
        }
    }

    public void setStatusManager(@NotNull GameManager sm) {
        this.manager = sm;
    }

    public void setGameRender(@NotNull GameRender gameRender) {
        this.render = gameRender;
    }

    public boolean isWaitingInput() {
        return roundController.isWaitingInput();
    }

    public void setWaitingInput(boolean newRound) {
        roundController.setWaitingInput(newRound);
    }

    public int[] getBallPosition() {
        return manager.getBallPosition();
    }

    public List<int[]> getPinsPosition() {
        return manager.getPinsPosition();
    }

    public int getHook() {
        return manager.getHook().getValue();
    }

    public int getPower() {
        return manager.getPower().getValue();
    }

    public boolean hookIsSet() {
        return manager.getHook().valueIsSet();
    }

    public boolean powerIsSet() {
        return manager.getPower().valueIsSet();
    }

    public void resetBall() {
        manager.resetBall();
    }

    public void resetProgressBar() {
        render.resetProgressbar();
    }

    public void resetItems() {
        manager.getHook().reset();
        manager.getPower().reset();
    }

    private class Inputprocessor {
        void processInput() {
            KeyStroke key;
            try {
                key = GameController.this.screen.pollInput();
            } catch (IOException e) {
                throw new RuntimeException("Inputprocess exception: ", e);
            }
            if (key != null) {
                switch (key.getKeyType()) {
                    case Enter:
                        if (!manager.valueIsSet(manager.getPower())) {
                            manager.setValue(manager.getPower(), render.getProgressbar());
                            render.resetProgressbar();
                            break;
                        }
                        if (!manager.valueIsSet(manager.getHook())) {
                            manager.setValue(manager.getHook(), render.getProgressbar());
                            setWaitingInput(false);
                            break;
                        }
                    case Escape:
                        stop = true;
                        break;
                    case ArrowLeft:
                        ballController.moveBallToLeft();
                        break;
                    case ArrowRight:
                        ballController.moveBallToRight();
                        break;
                    default:
                        break;
                }
            }

        }

    }

    private class PinsController {
        private List<Pin> collided = new ArrayList<>();

        void collisionHandler() {
            CollisionController cc = collisionController;
            Objects.requireNonNull(collided);
            collided.stream()
                    .forEach(Pin::move);
            //TODO
        }

        void update() {
            //TODO
        }

        List<Pin> getPins() {
            return GameController.this.manager.getPins();
        }

    }

    private class BallController {
        private boolean gutterBall = false;
        private Ball b;
        private RoundController r;
        private CollisionController c;

        BallController() {
        }

        void update() {
            b = b == null ? GameController.this.manager.getBall() : b;
            r = r == null ? GameController.this.roundController : r;
            c = c == null ? GameController.this.collisionController : c;

            Hook h = GameController.this.manager.getHook();
            if (!cached(b)) {
                int v = h.getValue();
                //offset is cot function
                double in = (double) h.getValue();
                UnaryOperator<Double> cal = a -> {
                    if (a == 23D) return 0D;
                    return (1.8 * 5 * Math.abs(a - 23) / 23) / 16;
                };
                double tmp = in > 23D ? cal.apply(in)
                        : -cal.apply(in);
                b.setOffset(tmp);

            }
            if (r.tossIsFinished()) {
                r.startNewRound();
                return;
            }
            b.move();
            c.detect();

        }

        boolean cached(Ball b) {
            return b.hasOffset();
        }

        boolean intersects(List<Pin> pins) {

            return GameController.this.collisionController.intersects(pins);
        }


        void moveBallToLeft() {
            moveBall(GlobalConstants.DirectionEnum.LEFT);
        }

        void moveBallToRight() {
            moveBall(GlobalConstants.DirectionEnum.RIGHT);
        }


        boolean isGutterBall() {
            return GameController.this.manager.getBall().gutterBall();
        }

        void gutterBallHandler() {
            //TODO
        }

        void collisionHandler() {
            PinsController pc = GameController.this.pinsController;
            manager.getPinsPosition()
                    .stream()
                    .filter(this::intersects)
                    .forEach(p -> pc.collided.add(new Pin(p)));
            //ball collides all collided pins with base likelihood
            ballController.removeCollidedPins(pc.collided);
        }

        private void moveBall(GlobalConstants.DirectionEnum d) {
            GameController.this.manager.moveSpot(d);
        }

        private boolean intersects(int[] p) {
            return collisionController.intersects(p);
        }

        private void removeCollidedPins(List<Pin> collided) {
            double baseLikelihood = b.getBaseLikelihood();
            collided.stream()
                    .forEach(p -> {

                    });
        }
    }

    private class CollisionController {
        void detect() {
            BallController bc = GameController.this.ballController;
            PinsController pc = GameController.this.pinsController;
            if (!bc.intersects(GameController.this.manager.getPins())) {
                if (bc.isGutterBall()) {
                    bc.gutterBallHandler();
                }
                return;
            }
            bc.collisionHandler();
            pc.collisionHandler();
        }

        boolean intersects(List<Pin> pins) {
            return pins.stream()
                    .anyMatch(this::intersects);
        }

        boolean intersects(Pin _p) {
            return !(_p.getPosition()[1] > 16 || GameController.this.manager.getBall().gutterBall())
                    || GameController.this.manager.getPinsPosition()
                    .stream()
                    .anyMatch(p -> GameController.this.manager.getBall().getPosition()[0] + 1 == _p.getPosition()[0]
                            || GameController.this.manager.getBall().getPosition()[0] - 1 == _p.getPosition()[0]
                            || GameController.this.manager.getBall().getPosition()[1] - 1 == _p.getPosition()[1]);
        }

        boolean intersects(int[] _p) {
            return !(_p[1] > 16 || GameController.this.manager.getBall().gutterBall())
                    || GameController.this.manager.getPinsPosition()
                    .stream()
                    .anyMatch(p -> GameController.this.manager.getBall().getPosition()[0] + 1 == _p[0])
                    || GameController.this.manager.getBall().getPosition()[0] - 1 == _p[0]
                    || GameController.this.manager.getBall().getPosition()[1] - 1 == _p[1];
        }

    }
}

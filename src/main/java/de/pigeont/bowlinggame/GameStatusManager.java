package de.pigeont.bowlinggame;

import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.model.Ball;
import de.pigeont.bowlinggame.model.Hook;
import de.pigeont.bowlinggame.model.Pins;
import de.pigeont.bowlinggame.model.Power;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

final class GameStatusManager {
    private static GameStatusManager gameManager;
    private final Logger logger;
    private final BallController ballController;
    private final RoundController roundController;
    private GameController gameController;
    private Long gameControllerID = 0L;
    private Pins pins;
    private Ball ball;
    private Power power;
    private Hook hook;

    private GameStatusManager(Logger l) {
        logger = l;
        ballController = new BallController();
        roundController = new RoundController();
    }

    @NotNull
    static GameStatusManager createGameStatusManager(@NotNull Logger lc) {
        gameManager = null == gameManager ? new GameStatusManager(lc) : gameManager;
        return gameManager;
    }

    void init() {
        pins = new Pins();
        ball = new Ball();
        power = new Power();
        hook = new Hook();
        roundController.setGameStatusManager(this);
    }

    void update() {
        if (isWaitingInput()) return;
        //update ball position
        ballController.update();
        //update pins
        pins.update(ball);
    }

    void setGameController(@NotNull GameController gameController) {
        if (0L != gameControllerID) throw new RuntimeException("reassignment of render of game constrolelr");
        this.gameController = gameController;
        gameControllerID = 1L;
    }

    boolean powerIsSet() {
        return power.powerIsSet();
    }

    boolean hookIsSet() {
        return hook.hookIsSet();
    }

    boolean isWaitingInput() {
        return roundController.isWaitingInput();
    }

    public void setWaitingInput(boolean newRound) {
        roundController.setWaitingInput(newRound);
    }

    public void resetNewRound() {
        ball.resetNewRound();
        roundController.startNewRound();
        hook.resetNewRound();
        power.resetNewRound();
    }

    public Integer[] getBallPosition() {
        return ball.getPosition();
    }

    public List<Integer[]> getPinsPosition() {
        return pins.getPinsPosition();
    }

    Integer getPower() {
        return power.getPower();
    }

    void setPower(Integer power) {
        this.power.setPower(power);
    }

    Integer getHook() {
        return hook.getHook();
    }

    void setHook(Integer hook) {
        this.hook.setHook(hook);
    }

    private class BallController {
        private BallController() {
        }

        void update() {
            Hook h = GameStatusManager.this.hook;
            Ball b = GameStatusManager.this.ball;
            if (cached(b)) {
                if (ball.checkResetCondition()) {
                    gameController.resetNewRound();
                    return;
                }
                ball.move();
                return;
            }
            Integer v = h.getHook();

            //offset is cot function
            //math fomular is like:
            //assuming h is hook value
            //the x mean unit x length
            //offset = f(h) = 16/(3/11 * |h - 23|) * x
            double in = Double.valueOf(h.getHook());
            UnaryOperator<Double> cal = a -> {
                if (23D == a) return 0D;
                return (5 * Math.abs(a - 23) / 23) / 16;
            };
            Double tmp = 23D < in ? cal.apply(in)
                    : -cal.apply(in);
            b.setOffsetVector(tmp);
        }

        boolean cached(Ball b) {
            return b.isOffSetVectorSet();
        }
    }

}

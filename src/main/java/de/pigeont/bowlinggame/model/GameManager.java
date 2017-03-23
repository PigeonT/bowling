package de.pigeont.bowlinggame.model;

import de.pigeont.bowlinggame.constants.GlobalConstants;
import de.pigeont.bowlinggame.controller.GameController;
import de.pigeont.bowlinggame.model.items.Hook;
import de.pigeont.bowlinggame.model.items.Item;
import de.pigeont.bowlinggame.model.items.Power;
import de.pigeont.bowlinggame.model.sprites.Ball;
import de.pigeont.bowlinggame.model.sprites.Pin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class GameManager {
    private static GameManager gameManager;
    private final Ball ball = new Ball();
    private final List<Pin> pins = new ArrayList<>(10);
    private final Power power = new Power();
    private final Hook hook = new Hook();
    private List<int[]> pinsPosition = GlobalConstants.ORIGIN_PINS;

    private GameManager() {
    }

    @NotNull
    public static GameManager createGameStatusManager() {
        gameManager = gameManager == null ? new GameManager() : gameManager;
        return gameManager;
    }

    public void moveSpot(GlobalConstants.DirectionEnum d) {
        ball.moveSpot(d);
    }

    public void setGameController(@NotNull GameController gameController) {
    }

    public boolean valueIsSet(Item item) {
        return item.valueIsSet();
    }

    public int[] getBallPosition() {
        return ball.getPosition();
    }

    public List<int[]> getPinsPosition() {
        return pinsPosition;
    }

    public Power getPower() {
        return power;
    }

    public Hook getHook() {
        return hook;
    }

    public Ball getBall() {
        return ball;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public void setValue(Item item, int value) {
        item.setValue(value);
    }

    public void resetBall() {
        ball.resetBall();
    }

}

package de.pigeont.bowlinggame.gameobjects;

import com.sun.istack.internal.NotNull;
import de.pigeont.bowlinggame.controller.GameController;
import de.pigeont.bowlinggame.gameobjects.model.Ball;
import de.pigeont.bowlinggame.gameobjects.model.Hook;
import de.pigeont.bowlinggame.gameobjects.model.Pins;
import de.pigeont.bowlinggame.gameobjects.model.Power;

import java.util.List;
import java.util.logging.Logger;

public final class GameStatusManager {
    private static GameStatusManager gameManager;
    private final Logger logger;
    private GameController gameController;
    private Long gameControllerID = 0L;
    private Pins pins;
    private Ball ball;
    private Power power;
    private Hook hook;

    private GameStatusManager(Logger l) {
        logger = l;
    }

    @NotNull
    public static GameStatusManager createGameStatusManager(@NotNull Logger lc) {
        gameManager = null == gameManager ? new GameStatusManager(lc) : gameManager;
        return gameManager;
    }

    public void init() {
        pins = new Pins();
        ball = new Ball(15, 18);
        power = new Power();
        hook = new Hook();
    }

    public void update() {

    }

    public void setGameController(@NotNull GameController gameController) {
        if (0L != gameControllerID) throw new RuntimeException("reassignment of render of game constrolelr");
        this.gameController = gameController;
        gameControllerID = 1L;
    }

    public List<Integer[]> getOriginPins() {
        return Pins.ORIGIN_PIN;
    }

    public boolean powerIsSet() {
        return power.powerIsSet();
    }

    public boolean hookIsSet() {
        return hook.hookIsSet();
    }

    public void setPower(Integer power) {
        this.power.setPower(power);
    }

    public void setHook(Integer hook) {
        this.hook.setHook(hook);
    }
}

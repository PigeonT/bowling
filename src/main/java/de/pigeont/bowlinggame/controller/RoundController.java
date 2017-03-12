package de.pigeont.bowlinggame.controller;

import de.pigeont.bowlinggame.constants.GlobalConstants;
import de.pigeont.bowlinggame.model.game.Round;
import de.pigeont.bowlinggame.model.game.Toss;
import de.pigeont.bowlinggame.utils.Pair;

import java.util.ArrayList;
import java.util.List;

final class RoundController {
    private final GlobalConstants.RoundType SPARE = GlobalConstants.RoundType.SPARE;
    private final GlobalConstants.RoundType STRIKE = GlobalConstants.RoundType.STRIKE;
    private final List<Round> results = new ArrayList<>(10);
    private final List<Round> pendingRounds = new ArrayList<>(3);
    private boolean waitingInput = true;
    private boolean spare = false;
    private boolean strike = false;
    private final GameController gameController;
    private Toss toss1;
    private Toss toss2;
    private Toss currentToss = toss1;
    private int currentRoundIndex = 0;
    private GlobalConstants.RoundType currentRoundType;
    private boolean gutterBall;

    public RoundController(GameController gc) {
        this.gameController = gc;
    }

    public void startNewRound() {
        //TODO save current round
        //saveCurrentRound();
        currentRoundIndex++;
        waitingInput = true;
        gameController.resetBall();
        gameController.resetProgressBar();
        gameController.resetItems();
    }

    public boolean tossIsFinished() {
        return isGutterBall() || ballIsOutofRange();
    }

    private boolean ballIsOutofRange() {
        return gameController.getBallPosition()[1] < 2;
    }

    private void saveCurrentRound() {
        if (strike) {
            pendingRounds.add(
                    new Round(currentRoundIndex,
                            currentToss.getScore(),
                            currentRoundType,
                            new Pair<>(
                                    toss1,
                                    toss2)));
            addToResults();
            checkMaxAllowedScore();
            return;
        }
        updatePendingRounds();
        clearPendingRounds();
    }

    @Override
    public String toString() {
        return currentToss.toString();
    }

    private void clearPendingRounds() {
        pendingRounds.clear();
    }

    private void checkMaxAllowedScore() {
        if (pendingRounds.size() == 3) {
            Round r = results.get(pendingRounds.get(0).getIndex());
            r.setScore(GlobalConstants.MAX_POINT_PER_ROUND);
            pendingRounds.remove(0);
        }
    }

    private void updatePendingRounds() {
        pendingRounds
                .stream()
                .forEach(r -> {
                    int s = 10 * (currentRoundIndex - r.getIndex())
                            + currentToss.getScore();
                    Round rr = results.get(r.getIndex());

                    rr.setScore(s);
                });
    }

    private void addToResults() {
        results.add(new Round(currentRoundIndex,
                currentToss.getScore(),
                currentRoundType,
                new Pair<>(
                        toss1,
                        toss2)));
    }

    public boolean isWaitingInput() {
        return waitingInput;
    }

    public void setWaitingInput(boolean waitingInput) {
        this.waitingInput = waitingInput;
    }

    public boolean isGutterBall() {
        return gameController.isGutterBall();
    }

}

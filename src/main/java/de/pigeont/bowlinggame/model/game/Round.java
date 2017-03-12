package de.pigeont.bowlinggame.model.game;

import de.pigeont.bowlinggame.constants.GlobalConstants;
import de.pigeont.bowlinggame.utils.Pair;

public final class Round implements Game {
    private final int MAX_POINT_PER_ROUND = GlobalConstants.MAX_POINT_PER_ROUND;
    int score;
    private final int index;
    private final GlobalConstants.RoundType type;
    private final Pair<Toss, Toss> tosses;
    private boolean pending;

    public Round(int index, int score, GlobalConstants.RoundType type, Pair<Toss, Toss> tosses) {
        this.index = index;
        this.score = score;
        this.type = type;
        this.tosses = tosses;
    }


    @Override
    public String toString() {
        return "round: "
                + this.index
                + "score: "
                + this.score;
    }

    public GlobalConstants.RoundType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    //TODO
    public Pair<Toss, Toss> getTosses() {
        return tosses;
    }

    //TODO
    public boolean isPending() {
        return score == GlobalConstants.EMPTY;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setScore(int v) {

    }
}

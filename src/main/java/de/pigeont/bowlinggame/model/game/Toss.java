package de.pigeont.bowlinggame.model.game;

public final class Toss implements Game {
    private int score;

    public Toss(int s) {
        this.score = s;
    }

    @Override
    public String toString() {
        return "toss: " + String.valueOf(score);
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setScore(int v) {

    }
}

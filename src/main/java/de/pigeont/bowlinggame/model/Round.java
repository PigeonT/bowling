package de.pigeont.bowlinggame.model;

public final class Round {
    Integer score;
    final Integer name;

    public Round(Integer name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public Round(Integer name) {
        this(name, 0);
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "round: "
                + this.name
                + "score: "
                + this.score;
    }
}

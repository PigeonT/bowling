package de.pigeont.bowlinggame.model;

public final class Power {
    private static final Integer EMPTY = -1;
    private Integer power;

    public Power() {
        this.power = EMPTY;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }

    public boolean powerIsSet() {
        return !EMPTY.equals(power);
    }
}

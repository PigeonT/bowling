package de.pigeont.bowlinggame.model.items;

import de.pigeont.bowlinggame.constants.GlobalConstants;

public final class Power implements Item {
    private int power = GlobalConstants.EMPTY;

    public Power() {
    }


    @Override
    public String toString() {
        return "power: " + String.valueOf(power);
    }


    @Override
    public int getValue() {
        return power;
    }

    @Override
    public void setValue(int v) {
        power = v;
    }

    @Override
    public boolean valueIsSet() {
        return power != GlobalConstants.EMPTY;
    }

    @Override
    public void reset() {
        power = GlobalConstants.EMPTY;
    }
}

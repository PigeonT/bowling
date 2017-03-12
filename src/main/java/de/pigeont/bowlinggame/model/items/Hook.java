package de.pigeont.bowlinggame.model.items;

import de.pigeont.bowlinggame.constants.GlobalConstants;

public final class Hook implements Item {
    private int hook = GlobalConstants.EMPTY;

    public Hook() {
    }

    @Override
    public int getValue() {
        return hook;
    }

    @Override
    public void setValue(int v) {
        hook = v;
    }

    @Override
    public boolean valueIsSet() {
        return this.hook != GlobalConstants.EMPTY;
    }

    @Override
    public void reset() {
        hook = GlobalConstants.EMPTY;
    }

    @Override
    public String toString() {
        return "hook: " + String.valueOf(hook);
    }


}

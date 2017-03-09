package de.pigeont.bowlinggame.gameobjects.model;

public final class Hook {
    private static final Integer EMPTY = -1;
    private Integer hook;

    public Hook() {
        this.hook = EMPTY;
    }

    public void setHook(Integer hook) {
        this.hook = hook;
    }

    public Integer getHook() {
        return hook;
    }

    public boolean hookIsSet() {
        return !EMPTY.equals(hook);
    }
}

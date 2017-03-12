package de.pigeont.bowlinggame.model.items;

public interface Item {
    int getValue();

    void setValue(int v);

    boolean valueIsSet();

    void reset();
}

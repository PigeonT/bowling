package de.pigeont.bowlinggame.model.sprites;

public interface Sprite {
    int[] getPosition();

    void setPosition(int[] position);

    void move();
}

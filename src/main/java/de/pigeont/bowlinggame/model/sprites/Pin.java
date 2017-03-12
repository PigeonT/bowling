package de.pigeont.bowlinggame.model.sprites;

import java.util.Random;

public final class Pin implements Sprite {
    private int[] position;

    public Pin(int[] position) {
        this.position = position;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public void setPosition(int[] position) {
        this.position = position;
    }

    @Override
    public void move() {
        moveRandom();
    }

    private Pin moveRandom() {
        int dir = new Random().nextInt(3);
        switch (dir) {
            case 0:
                position[0]--;
            case 1:
                position[0]++;
            case 2:
                position[1]--;
            default:
                break;
        }
        return this;
    }

    @Override
    public String toString() {
        return " pin X: " + String.valueOf(position[0])
                + " pin Y: " + String.valueOf(position[1]);
    }
}

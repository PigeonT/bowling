package de.pigeont.bowlinggame.model.sprites;

//import de.pigeont.bowlinggame.constants.GlobalConstants;

import de.pigeont.bowlinggame.constants.GlobalConstants;

final public class Ball implements Sprite {
    private double offset = -1;
    private int strength = -1;
    private int[] position = new int[]{GlobalConstants.BALLORIGIN_X, GlobalConstants.BALLORIGIN_Y};
    private float baseLikelihood = -1;

    public Ball() {
    }

    @Override
    public void move() {
        position[1] -= 1;
        if (!hasOffset() || !hasOffset()) return;
        if (((position[1] - GlobalConstants.BALLORIGIN_Y) % Math.floor(Math.abs(1 / offset))) == 0) {
            if (offset > 0) position[0] += 1;
            if (offset < 0) position[0] -= 1;
        }
    }

    public void resetBall() {
        setOffset(GlobalConstants.EMPTY);
        setPosition(new int[]{GlobalConstants.BALLORIGIN_X, GlobalConstants.BALLORIGIN_Y});
    }

    public boolean hasOffset() {
        return offset != GlobalConstants.EMPTY;
    }


    public void moveSpot(GlobalConstants.DirectionEnum d) {
        if (allowLeftMove() && moveToLeft(d)) {
            position[0] -= 1;
            return;
        }
        if (allowRightMove() && moveToRight(d))
            position[0] += 1;
    }


    public boolean gutterBall() {
        return position[0] < 11 || position[0] > 19;
    }

    //TODO
    public void setStrength(int strength) {
        float _s = (float) strength;
        this.strength = strength;
        //from 0 to 0.23
        this.baseLikelihood = (_s / 2) / 100;
    }


    public double getBaseLikelihood() {

        return baseLikelihood;
    }

    private boolean moveToLeft(GlobalConstants.DirectionEnum d) {
        return d == GlobalConstants.DirectionEnum.LEFT;
    }

    private boolean moveToRight(GlobalConstants.DirectionEnum d) {
        return d == GlobalConstants.DirectionEnum.RIGHT;
    }

    private boolean allowLeftMove() {
        return position[0] >= 12;
    }

    private boolean allowRightMove() {
        return position[0] <= 18;
    }

    @Override
    public String toString() {
        String offset = this.offset == -1 ? "not set" : String.valueOf(this.offset);
        return " ball X: " + String.valueOf(position[0])
                + " ball Y: " + String.valueOf(position[1])
                + " ball offset: " + offset;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

}

package de.pigeont.bowlinggame.model;

import java.util.Arrays;
import java.util.List;

public final class Pins {
    public static final List<Integer[]> ORIGIN_PIN = Arrays.asList(
            new Integer[][]{
                    {12, 2}, {14, 2}, {16, 2}, {18, 2},
                    {13, 3}, {15, 3}, {17, 3},
                    {14, 4}, {16, 4},
                    {15, 5}
            }
    );
    private List<Integer[]> currentPins;

    public Pins() {
        currentPins = ORIGIN_PIN;
    }

    @Override
    public String toString() {
        Long c = currentPins
                .stream()
                .count();
        return c.toString();
    }

    public List<Integer[]> getPinsPosition() {
        return currentPins;
    }

    public void setPinsPosition(List<Integer[]> pinsPosition) {
        this.currentPins = pinsPosition;
    }

    private static class Pin {
        private Integer x;
        private Integer y;

        Pin(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "pin X: " + this.x.toString()
                    + "pin Y: " + this.y.toString();
        }
    }


}

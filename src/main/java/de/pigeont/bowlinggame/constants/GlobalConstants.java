package de.pigeont.bowlinggame.constants;

import java.util.Arrays;
import java.util.List;

public final class GlobalConstants {
    public static final int COL = 96;
    public static final int ROW = 24;
    public static final int BALLORIGIN_X = 15;
    public static final int BALLORIGIN_Y = 18;
    public static final int LEFTDIR = -1;
    public static final int RIGHTDIR = 1;
    public static final int EMPTY = -1;
    public static final String LOGGER = "BolwingGame";
    public static final int MAX_POINT_PER_ROUND = 30;
    public static final List<int[]> ORIGIN_PINS = Arrays.asList(
            new int[][]{
                    {12, 2}, {14, 2}, {16, 2}, {18, 2},
                    {13, 3}, {15, 3}, {17, 3},
                    {14, 4}, {16, 4},
                    {15, 5}
            }
    );

    public enum RoundType {
        SPARE, STRIKE
    }

    public enum DirectionEnum {
        LEFT, RIGHT
    }
}

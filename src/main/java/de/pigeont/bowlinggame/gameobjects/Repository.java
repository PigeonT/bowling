package de.pigeont.bowlinggame.gameobjects;

import java.util.Arrays;
import java.util.List;

//mock like database in memory
public final class Repository {
    public static final List<Integer[]> ORIGIN_PIN = Arrays.asList(
            new Integer[][]{
                    {12, 2}, {14, 2}, {16, 2}, {18, 2},
                    {13, 3}, {15, 3}, {17, 3},
                    {14, 4}, {16, 4},
                    {15, 5}
            }
    );


}

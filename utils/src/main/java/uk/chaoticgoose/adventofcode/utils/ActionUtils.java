package uk.chaoticgoose.adventofcode.utils;

import java.util.stream.IntStream;

public final class ActionUtils {
    private ActionUtils() {}

    public static void repeat(int times, Runnable action) {
        IntStream.range(0, times).forEach(i -> action.run());
    }
}

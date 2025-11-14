package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

@NullMarked
class PairTest {
    @Test
    void parameterisesNullableTypes() {
        Pair<@Nullable Integer> underTest = new Pair<>(null, null);

        acceptsNull(underTest.left());
    }

    @Test
    void parameterisesNonNullTypes() {
        Pair<Integer> underTest = new Pair<>(1, 2);

        acceptsNonNull(underTest.left());
    }

    private static void acceptsNull(@Nullable Integer ignore) {
        // does nothing
    }

    private static void acceptsNonNull(Integer ignore) {
        // does nothing
    }
}
package uk.chaoticgoose.adventofcode.utils.collectors;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public final class Collectors {
    private Collectors() {}

    public static <T> Collector<@Nullable T, ?, List<@Nullable T>> toListOfNullables() {
        return Collector.<@Nullable T, List<@Nullable T>>of(
            ArrayList::new,
            List::add,
            (left, right) -> {
                left.addAll(right);
                return left;
            },
            Collector.Characteristics.IDENTITY_FINISH
        );
    }
}

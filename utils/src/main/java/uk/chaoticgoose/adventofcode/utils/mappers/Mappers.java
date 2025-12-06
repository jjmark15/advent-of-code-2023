package uk.chaoticgoose.adventofcode.utils.mappers;

import java.util.List;
import java.util.function.Function;

public final class Mappers {
    private Mappers() {}

    public static <T, U> Function<List<T>, List<U>> mapInner(Function<T, U> mapper) {
        return input -> input.stream().map(mapper).toList();
    }
}

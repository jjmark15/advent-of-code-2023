package uk.chaoticgoose.adventofcode.utils.mappers;

import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.function.Function;

@NullMarked
public final class Mappers {
    private Mappers() {}

    public <T, U> Function<List<T>, List<U>> mapInner(Function<T, U> mapper) {
        return input -> input.stream().map(mapper).toList();
    }
}

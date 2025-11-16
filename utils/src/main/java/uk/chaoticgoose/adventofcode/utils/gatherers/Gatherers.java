package uk.chaoticgoose.adventofcode.utils.gatherers;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.stream.Gatherer;

@NullMarked
public final class Gatherers {

    private Gatherers() {}

    public static <T extends @Nullable Object, R> Gatherer<T, Void, R> mapNonNull(final Function<T, @Nullable R> mapper) {
        return Gatherer.of(
            (_, element, downstream) -> {
                R result = mapper.apply(element);
                if (result != null) {
                    return downstream.push(result);
                }
                return true;
            }
        );
    }
}

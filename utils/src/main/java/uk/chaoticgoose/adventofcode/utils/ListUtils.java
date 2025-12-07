package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Gatherers.fold;

public final class ListUtils {
    private ListUtils() {}

    public static <T extends @Nullable Object> Optional<T> get(List<T> list, int index) {
        if (list.size() <= index ) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(index));
    }

    public static <T extends @Nullable Object> @Nullable T getOrNull(List<T> list, int index) {
        return get(list, index).orElse(null);
    }

    public static <T extends @Nullable Object> List<List<T>> separateListBy(List<T> list, Predicate<T> predicate) {
        return list.stream()
            .gather(fold(
                () -> new ArrayList<>(List.of(new ArrayList<T>())),
                (acc, curr) -> {
                    if (predicate.test(curr) && !acc.getLast().isEmpty()) {
                        acc.add(new ArrayList<>());
                        return acc;
                    }

                    acc.getLast().add(curr);
                    return acc;
                }
            ))
            .flatMap(Collection::stream)
            .map(l -> l.stream().toList())
            .toList();
    }
}

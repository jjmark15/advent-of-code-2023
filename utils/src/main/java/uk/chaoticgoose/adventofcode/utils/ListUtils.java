package uk.chaoticgoose.adventofcode.utils;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

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
}

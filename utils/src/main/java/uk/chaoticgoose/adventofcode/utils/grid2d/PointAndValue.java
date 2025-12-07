package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.Nullable;

import static java.util.Objects.requireNonNull;

public record PointAndValue<T extends @Nullable Object>(Point2D point, T value) {
    public PointAndValue {
        requireNonNull(point);
    }
}

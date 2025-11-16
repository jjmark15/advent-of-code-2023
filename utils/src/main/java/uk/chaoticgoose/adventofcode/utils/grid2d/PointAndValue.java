package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record PointAndValue<T>(Point2D point, @Nullable T value) {
}

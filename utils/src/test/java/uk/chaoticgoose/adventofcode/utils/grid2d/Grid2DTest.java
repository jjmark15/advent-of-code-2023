package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@NullMarked
class Grid2DTest {

    @Test
    void fillsWithNullsWhenCreatedFromSize() {
        Grid2D<@Nullable String> underTest = Grid2D.ofSize(1, 1, null);

        assertThat(underTest.get(new Point2D(0, 0))).isEmpty();
        assertThat(underTest.getOrNull(new Point2D(0, 0))).isNull();
    }

    @Test
    void mapsValues() {
        Grid2D<String> underTest = Grid2D.ofSize(1, 1, "value");

        Grid2D<Integer> result = underTest.map(String::length);

        assertThat(result.get(new Point2D(0, 0))).hasValue(5);
    }

    @Test
    void setsValues() {
        Grid2D<@Nullable String> underTest = Grid2D.ofSize(1, 1, null);
        underTest.set(new Point2D(0, 0), "value1");

        underTest.set(new Point2D(0, 0), "value2");

        assertThat(underTest.get(new Point2D(0, 0))).hasValue("value2");
    }

    private static Stream<Arguments> containsPointsInGrid() {
        return Stream.of(
            arguments(0, 0, new Point2D(-1, -1), false),
            arguments(0, 0, new Point2D(-1, 0), false),
            arguments(0, 0, new Point2D(0, -1), false),
            arguments(0, 0, new Point2D(0, 0), false),

            arguments(0, 0, new Point2D(0, 0), false),
            arguments(0, 0, new Point2D(0, 1), false),
            arguments(0, 0, new Point2D(1, 0), false),
            arguments(0, 0, new Point2D(1, 1), false),

            arguments(1, 0, new Point2D(0, 0), false),

            arguments(1, 1, new Point2D(0, 0), true),
            arguments(1, 1, new Point2D(0, 1), false),
            arguments(1, 1, new Point2D(1, 0), false),
            arguments(1, 1, new Point2D(1, 1), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void containsPointsInGrid(int height, int width, Point2D point, boolean contained) {
        Grid2D<@Nullable String> underTest = Grid2D.ofSize(height, width, null);

        assertThat(underTest.contains(point)).isEqualTo(contained);
    }

    @Test
    void matchesValuesWithPredicate() {
        Grid2D<@Nullable Integer> underTest = new Grid2D<>(List.of(Arrays.asList(null, 1, 2, 3)));

        List<@Nullable Integer> result = underTest.valuesMatching((point, v) -> v != null && v > 1 && point.x() < 3);

        assertThat(result).containsExactly(2);
    }
}
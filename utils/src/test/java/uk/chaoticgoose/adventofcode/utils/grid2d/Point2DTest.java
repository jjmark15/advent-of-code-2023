package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class Point2DTest {
    @ParameterizedTest
    @MethodSource
    void movesInDirection(int startX, int startY, Direction2D direction, int endX, int endY) {
        Point2D result = new Point2D(startX, startY).toThe(direction);
        assertThat(result).isEqualTo(new Point2D(endX, endY));
    }

    private static Stream<Arguments> movesInDirection() {
        return Stream.of(
            arguments(0, 1, Direction2D.North, 0, 0),
            arguments(0, 0, Direction2D.South, 0, 1),
            arguments(0, 0, Direction2D.East, 1, 0),
            arguments(1, 0, Direction2D.West, 0, 0)
        );
    }

    @Test
    void returnsCardinalNeighbours() {
        assertThat(new Point2D(1, 1).cardinalNeighbours()).containsExactlyInAnyOrder(
            new Point2D(1, 0),
            new Point2D(1, 2),
            new Point2D(0, 1),
            new Point2D(2, 1)
        );
    }
}
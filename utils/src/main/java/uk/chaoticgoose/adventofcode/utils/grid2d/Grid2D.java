package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.ginsberg.gatherers4j.Gatherers4j.mapIndexed;
import static java.util.stream.Collectors.joining;
import static uk.chaoticgoose.adventofcode.utils.collectors.Collectors.toListOfNullables;

public class Grid2D<T extends @Nullable Object> {
    private final ArrayList<ArrayList<@Nullable T>> inner;
    private final int height;
    private final int width;

    public Grid2D(List<? extends List<@Nullable T>> map) {
        height = map.size();
        width = map.isEmpty() ? 0 : map.getFirst().size();

        inner = new ArrayList<>();
        for (List<T> row : map) {
            if (row.size() != width) {
                throw new IllegalArgumentException("row size " + row.size() + " does not match width " + width);
            }
            inner.add(new ArrayList<>(row));
        }
    }

    public static <T extends @Nullable Object> Grid2D<T> ofSize(int width, int height, T defaultValue) {
        return new Grid2D<>(IntStream.range(0, height)
            .mapToObj(_ -> IntStream.range(0, width).mapToObj(_ -> defaultValue).collect(toListOfNullables()))
            .toList());
    }

    public Optional<T> get(Point2D point) {
        if (contains(point)) {
            T value = inner.get(point.y()).get(point.x());
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

    @Nullable
    public T getOrNull(Point2D point) {
        return get(point).orElse(null);
    }

    public boolean contains(Point2D point) {
        return 0 <= point.y() && height > point.y() && 0 <= point.x() && width > point.x();
    }

    public void set(Point2D point, T value) {
        throwIfNotInGrid(point);
        this.inner.get(point.y()).set(point.x(), value);
    }

    public <R> Grid2D<R> map(Function<? super T, R> mapper) {
        return new Grid2D<>(inner.stream()
            .map(row -> row.stream().map(mapper).collect(toListOfNullables()))
            .collect(toListOfNullables()));
    }

    public List<T> valuesMatching(BiPredicate<Point2D, T> predicate) {
        return streamIndexed()
            .filter(pv -> predicate.test(pv.point(), pv.value()))
            .map(PointAndValue::value)
            .toList();
    }

    public List<Point2D> pointsMatching(BiPredicate<Point2D, T> predicate) {
        return streamIndexed()
            .filter(pv -> predicate.test(pv.point(), pv.value()))
            .map(PointAndValue::point)
            .toList();
    }

    public Stream<PointAndValue<T>> streamIndexed() {
        return inner.stream()
            .gather(mapIndexed((rowIndex, row) -> row.stream()
                .gather(mapIndexed((columnIndex, value) ->
                    new PointAndValue<>(new Point2D(columnIndex, rowIndex), value)))))
            .flatMap(s -> s);
    }

    private void throwIfNotInGrid(Point2D point) {
        if (!contains(point)) {
            throw new IllegalArgumentException("point y: %s x: %s is not in the grid".formatted(point.y(), point.x()));
        }
    }

    @SuppressWarnings("unused")
    public String debugString(Function<T, String> mapper) {
        return inner.stream()
            .map(line -> line.stream().map(mapper).collect(joining()))
            .collect(joining("\n"));
    }
}

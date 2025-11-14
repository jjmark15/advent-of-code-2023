package uk.chaoticgoose.adventofcode.utils.grid2d;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.ginsberg.gatherers4j.Gatherers4j.mapIndexed;
import static uk.chaoticgoose.adventofcode.utils.collectors.Collectors.toListOfNullables;

@NullMarked
public class Grid2D<T> {
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

    public static <T> Grid2D<T> ofSize(int height, int width) {
        return new Grid2D<>(IntStream.range(0, height)
            .mapToObj(_ -> IntStream.range(0, width).mapToObj(_ -> (T) null).collect(toListOfNullables()))
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

    public void remove(Point2D point) {
        this.set(point, null);
    }

    public void set(Point2D point, @Nullable T value) {
        throwIfNotInGrid(point);
        this.inner.get(point.y()).set(point.x(), value);
    }

    public <R> Grid2D<R> map(Function<? super T, R> mapper) {
        return new Grid2D<>(inner.stream()
            .map(row -> row.stream().map(mapper).collect(toListOfNullables()))
            .collect(toListOfNullables()));
    }

    public List<T> valuesMatching(BiPredicate<Point2D, @Nullable T> predicate) {
        return inner.stream()
            .gather(mapIndexed((rowIndex, row) -> row.stream()
                .gather(mapIndexed((columnIndex, value) ->
                    new Tuple<>(new Point2D(columnIndex, rowIndex), value)))))
            .flatMap(s -> s)
            .filter(t -> predicate.test(t.left(), t.right()))
            .map(Tuple::right)
            .toList();
    }

    private void throwIfNotInGrid(Point2D point) {
        if (!contains(point)) {
            throw new IllegalArgumentException("point y: %s x: %s is not in the grid".formatted(point.y(), point.x()));
        }
    }
}

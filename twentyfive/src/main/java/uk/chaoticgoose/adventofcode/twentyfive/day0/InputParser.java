package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.jspecify.annotations.NullMarked;
import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.intr;

@NullMarked
class InputParser {
    private final Parser<Chr, Pair<Integer>> line = intr.andL(chr(' ').many1()).and(intr).map(Pair::new);

    Day0Solution.LocationIds parse(List<String> lines) {
        Gatherer<Pair<Integer>, ?, Pair<ArrayList<Long>>> fold = Gatherers.fold(
            () -> new Pair<>(new ArrayList<>(), new ArrayList<>()),
            (acc, curr) -> {
                acc.left.add(curr.left.longValue());
                acc.right.add(curr.right.longValue());
                return acc;
            });

        return lines.stream().map(l -> line.parse(Input.of(l)).getOrThrow()).toList().stream()
            .gather(fold)
            .findFirst()
            .map(pair -> new Day0Solution.LocationIds(pair.left, pair.right))
            .orElseThrow();
    }

    @NullMarked
    private record Pair<T>(T left, T right) {
    }
}

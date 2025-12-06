package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;
import uk.chaoticgoose.adventofcode.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.intr;

class InputParser {
    private final Parser<Chr, Pair<Integer>> line = intr.andL(chr(' ').many1()).and(intr).map(Pair::new);

    Pair<List<Long>> parse(List<String> lines) {
        return lines.stream().map(l -> line.parse(Input.of(l)).getOrThrow())
            .gather(Gatherers.fold(
                () -> new Pair<ArrayList<Long>>(new ArrayList<>(), new ArrayList<>()),
                (acc, curr) -> {
                    acc.left().add(curr.left().longValue());
                    acc.right().add(curr.right().longValue());
                    return acc;
                }))
            .findFirst()
            .map(p -> new Pair<List<Long>>(p.left(), p.right()))
            .orElseThrow();
    }
}

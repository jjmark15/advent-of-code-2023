package uk.chaoticgoose.adventofcode.twentyfive.day2;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.Arrays;
import java.util.List;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.lng;

class InputParser {
    private final Parser<Chr, IdRange> idRangeParser = lng.andL(chr('-')).and(lng).map(IdRange::new);

    List<IdRange> parse(List<String> lines) {
        return Arrays.stream(lines.getFirst().split(",")).map(l -> idRangeParser.parse(Input.of(l)).getOrThrow()).toList();
    }
}

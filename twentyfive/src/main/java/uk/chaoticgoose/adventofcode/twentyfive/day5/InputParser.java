package uk.chaoticgoose.adventofcode.twentyfive.day5;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;
import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.List;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.lng;
import static uk.chaoticgoose.adventofcode.utils.ListUtils.separateListBy;

class InputParser {
    private final Parser<Chr, NumberRange> numberRangeParser = lng.andL(chr('-')).and(lng).map(NumberRange::new);

    Tuple<List<NumberRange>, List<Long>> parse(List<String> lines) {
        List<List<String>> lists = separateListBy(lines, String::isEmpty);

        List<NumberRange> numberRanges = lists.getFirst().stream()
            .map(line -> numberRangeParser.parse(Input.of(line)).getOrThrow())
            .toList();

        List<Long> numbers = lists.getLast().stream()
            .map(line -> lng.parse(Input.of(line)).getOrThrow())
            .toList();

        return new Tuple<>(numberRanges, numbers);
    }
}

package uk.chaoticgoose.adventofcode.twentyfive.day3;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.data.IList;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.List;

import static org.typemeta.funcj.parser.Text.digit;

class InputParser {
    private final Parser<Chr, Integer> digitToInt = digit.map(Chr::digit);
    private final Parser<Chr, IList<Integer>> digits = digitToInt.many();

    List<BatteryBank> parse(List<String> lines) {
        return lines.stream()
            .map(line -> new BatteryBank(digits.parse(Input.of(line)).getOrThrow().toList()))
            .toList();
    }
}

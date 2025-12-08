package uk.chaoticgoose.adventofcode.twentyfive.day8;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.List;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.lng;

class InputParser {
    private final Parser<Chr, Position3D> positionParser = lng.andL(chr(',')).and(lng).andL(chr(',')).and(lng).map(Position3D::new);

    List<Position3D> parse(List<String> lines) {
        return lines.stream()
            .map(line -> positionParser.parse(Input.of(line)).getOrThrow())
            .toList();
    }
}

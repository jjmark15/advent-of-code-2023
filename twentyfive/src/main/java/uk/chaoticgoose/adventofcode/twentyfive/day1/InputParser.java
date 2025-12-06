package uk.chaoticgoose.adventofcode.twentyfive.day1;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.List;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.lng;

class InputParser {
    private final Parser<Chr, Rotation.Direction> directionParser = chr('L').or(chr('R')).map(this::parseDirection);
    private final Parser<Chr, Rotation> rotationParser = directionParser.and(lng).map(Rotation::new);

    List<Rotation> parse(List<String> lines) {
        return lines.stream().map(line -> rotationParser.parse(Input.of(line)).getOrThrow()).toList();
    }

    private Rotation.Direction parseDirection(Chr chr) {
        return switch (chr.charValue()) {
            case 'L' -> Rotation.Direction.Left;
            case 'R' -> Rotation.Direction.Right;
            default -> throw new IllegalStateException("Unexpected value: " + chr.charValue());
        };
    }

}

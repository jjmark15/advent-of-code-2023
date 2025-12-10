package uk.chaoticgoose.adventofcode.twentyfive.day9;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;
import uk.chaoticgoose.adventofcode.utils.grid2d.Point2D;

import java.util.List;

import static org.typemeta.funcj.parser.Text.chr;
import static org.typemeta.funcj.parser.Text.intr;

class InputParser {
    private final Parser<Chr, Point2D> positionParser = intr.andL(chr(',')).and(intr).map(Point2D::new);

    List<Point2D> parse(List<String> lines) {
        return lines.stream().map(line -> positionParser.parse(Input.of(line)).getOrThrow()).toList();
    }
}

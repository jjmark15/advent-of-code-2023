package uk.chaoticgoose.adventofcode.twentyfive.day4;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.data.IList;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.List;

import static org.typemeta.funcj.parser.Text.chr;

class InputParser {
    private final Parser<Chr, MapItem> mapItemParser = chr('.').or(chr('@')).map(s -> switch (s.charValue()) {
        case '@' -> MapItem.Paper;
        case '.' -> MapItem.Empty;
        default -> throw new RuntimeException("Unexpected character: " + s);
    });
    private final Parser<Chr, List<MapItem>> lineParser = mapItemParser.many().map(IList::toList);

    List<List<MapItem>> parse(List<String> lines) {
        return lines.stream().map(line -> lineParser.parse(Input.of(line)).getOrThrow()).toList();
    }
}

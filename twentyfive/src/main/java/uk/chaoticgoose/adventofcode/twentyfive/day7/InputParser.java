package uk.chaoticgoose.adventofcode.twentyfive.day7;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.data.IList;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;

import java.util.List;

import static org.typemeta.funcj.parser.Parser.choice;
import static org.typemeta.funcj.parser.Text.chr;

class InputParser {
    private final Parser<Chr, DiagramElement> diagramElementParser = choice(chr('S'), chr('^'), chr('.')).map(this::parseDiagramElement);
    private final Parser<Chr, List<DiagramElement>> diagramLineParser = diagramElementParser.many().map(IList::toList);

    List<List<DiagramElement>> parse(List<String> lines) {
        return lines.stream().map(line -> diagramLineParser.parse(Input.of(line)).getOrThrow()).toList();
    }

    private DiagramElement parseDiagramElement(Chr chr) {
        return switch (chr.charValue()) {
            case 'S' -> DiagramElement.Start;
            case '^' -> DiagramElement.Splitter;
            case '.' -> DiagramElement.Empty;
            default -> throw new IllegalArgumentException("Invalid diagram element");
        };
    }
}

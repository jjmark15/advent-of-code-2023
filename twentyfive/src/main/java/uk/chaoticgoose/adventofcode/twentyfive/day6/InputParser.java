package uk.chaoticgoose.adventofcode.twentyfive.day6;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.data.IList;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Parser;
import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.List;

import static org.typemeta.funcj.parser.Parser.choice;
import static org.typemeta.funcj.parser.Text.*;

class InputParser {
    private final Parser<Chr, List<Long>> numberLineParser = ws.many().andR(lng.sepBy(ws.many())).map(IList::toList);
    private final Parser<Chr, Operator> operatorParser = ws.many()
        .andR(choice(chr('+'), chr('*')))
        .map(s -> parseOperator(s.charValue()));
    private final Parser<Chr, List<Operator>> operatorLineParser = operatorParser.sepBy(ws.many()).map(IList::toList);

    Tuple<List<List<Long>>, List<Operator>> parse(List<String> lines) {
        List<List<Long>> rows = lines.stream().limit(lines.size() - 1)
            .map(line -> numberLineParser.parse(Input.of(line)).getOrThrow())
            .toList();
        List<Operator> operators = operatorLineParser.parse(Input.of(lines.getLast())).getOrThrow();

        return new Tuple<>(rows, operators);
    }

    private Operator parseOperator(char s) {
        return switch (s) {
            case '+' -> Operator.Add;
            case '*' -> Operator.Multiply;
            default -> throw new IllegalArgumentException();
        };
    }
}

package uk.chaoticgoose.adventofcode.twentyfive.day6;

import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.Arrays;
import java.util.List;

class InputParser {
    Tuple<List<List<Long>>, List<Operator>> parse(List<String> lines) {
        List<List<Long>> rows = lines.stream().limit(lines.size() - 1)
            .map(line -> Arrays.stream(line.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong).toList())
            .toList();
        List<Operator> operators = Arrays.stream(lines.getLast().split("\\s+"))
            .filter(s -> !s.isEmpty())
            .map(this::parseOperator)
            .toList();

        return new Tuple<>(rows, operators);
    }

    private Operator parseOperator(String s) {
        return switch (s) {
            case "+" -> Operator.Add;
            case "*" -> Operator.Multiply;
            default -> throw new IllegalArgumentException();
        };
    }
}

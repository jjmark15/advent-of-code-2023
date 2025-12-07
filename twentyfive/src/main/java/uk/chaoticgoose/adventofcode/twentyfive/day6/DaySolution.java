package uk.chaoticgoose.adventofcode.twentyfive.day6;

import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.List;
import java.util.stream.IntStream;

class DaySolution {
    long part1(Tuple<List<List<Long>>, List<Operator>> input) {
        return transpose(input.left(), input.right()).stream().mapToLong(this::calculate).sum();
    }

    long part2(List<MathExpression> input) {
        return input.stream().mapToLong(this::calculate).sum();
    }

    private List<MathExpression> transpose(List<List<Long>> rows, List<Operator> operators) {
        return IntStream.range(0, operators.size())
            .mapToObj(i -> {
                List<Long> columnNumbers = rows.stream().map(row -> row.get(i)).toList();
                Operator operator = operators.get(i);
                return new MathExpression(columnNumbers, operator);
            }).toList();
    }

    private long calculate(MathExpression expression) {
        long result = expression.numbers().getFirst();

        for (int i = 1; i < expression.numbers().size(); i++) {
            final long n = expression.numbers().get(i);
            result = switch (expression.operator()) {
                case Add -> result + n;
                case Multiply -> result * n;
            };
        }

        return result;
    }
}

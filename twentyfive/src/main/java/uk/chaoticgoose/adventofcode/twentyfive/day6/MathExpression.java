package uk.chaoticgoose.adventofcode.twentyfive.day6;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

record MathExpression(List<Long> numbers, Operator operator) {
    MathExpression(List<Long> numbers, Operator operator) {
        this.numbers = unmodifiableList(numbers);
        this.operator = requireNonNull(operator);
    }
}

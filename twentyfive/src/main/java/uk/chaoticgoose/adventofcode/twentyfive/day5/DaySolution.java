package uk.chaoticgoose.adventofcode.twentyfive.day5;

import uk.chaoticgoose.adventofcode.utils.Tuple;

import java.util.List;

import static java.util.Comparator.comparing;

class DaySolution {
    long part1(Tuple<List<NumberRange>, List<Long>> input) {
        var numberRanges = input.left().stream().sorted(comparing(NumberRange::start)).toList();
        var numbers = input.right().stream().sorted().toList();

        long freshIngredients = 0;

        int offset = 0;

        for (var numberRange : numberRanges) {
            for (int i = offset; i < numbers.size(); i++) {
                long number = numbers.get(i);

                if (number > numberRange.end()) {
                    break;
                }

                if (number >= numberRange.start()) {
                    freshIngredients++;
                }

                offset++;
            }
        }

        return freshIngredients;
    }
}

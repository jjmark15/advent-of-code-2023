package uk.chaoticgoose.adventofcode.twentyfive.day7;

import org.junit.jupiter.api.Test;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.LONG;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.SHORT;

class Day7Test {
    private final TestInputLoader testInputLoader = new TestInputLoader();
    private final InputParser inputParser = new InputParser();
    private final DaySolution underTest = new DaySolution();

    @Test
    void part1Short() {
        assertThat(underTest.part1(inputParser.parse(data(SHORT)))).isEqualTo(21L);
    }

    @Test
    void part1Long() {
        assertThat(underTest.part1(inputParser.parse(data(LONG)))).isEqualTo(1546L);
    }

    @Test
    void part2Short() {
        assertThat(underTest.part2(inputParser.parse(data(SHORT)))).isEqualTo(40L);
    }

    @Test
    void part2Long() {
        assertThat(underTest.part2(inputParser.parse(data(LONG)))).isEqualTo(13883459503480L);
    }

    private List<String> data(InputDataModifier inputDataModifier) {
        return testInputLoader.load(2025, 7, inputDataModifier);
    }
}

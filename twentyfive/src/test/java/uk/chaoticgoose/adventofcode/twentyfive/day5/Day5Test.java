package uk.chaoticgoose.adventofcode.twentyfive.day5;

import org.junit.jupiter.api.Test;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.LONG;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.SHORT;

class Day5Test {
    private final TestInputLoader testInputLoader = new TestInputLoader();
    private final InputParser inputParser = new InputParser();
    private final DaySolution underTest = new DaySolution();

    @Test
    void part1Short() {
        assertThat(underTest.part1(inputParser.parse(data(SHORT)))).isEqualTo(3L);
    }

    @Test
    void part1Long() {
        assertThat(underTest.part1(inputParser.parse(data(LONG)))).isEqualTo(758L);
    }

    @Test
    void part2Short() {
        assertThat(underTest.part2(inputParser.parse(data(SHORT)))).isEqualTo(14L);
    }

    @Test
    void part2Long() {
        assertThat(underTest.part2(inputParser.parse(data(LONG)))).isEqualTo(343143696885053L);
    }

    private List<String> data(InputDataModifier inputDataModifier) {
        return testInputLoader.load(5, inputDataModifier);
    }
}

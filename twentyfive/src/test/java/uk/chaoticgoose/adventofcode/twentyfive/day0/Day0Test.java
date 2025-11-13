package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.junit.jupiter.api.Test;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.LONG;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.SHORT;

class Day0Test {
    private final TestInputLoader testInputLoader = new TestInputLoader();
    private final InputParser inputParser = new InputParser();
    private final Day0Solution underTest = new Day0Solution();

    @Test
    void part1Short() {
        assertThat(underTest.part1(inputParser.parse(data(SHORT)))).isEqualTo(11L);
    }

    @Test
    void part1Long() {
        assertThat(underTest.part1(inputParser.parse(data(LONG)))).isEqualTo(2430334L);
    }

    private List<String> data(InputDataModifier inputDataModifier) {
        return testInputLoader.load(2025, 0, inputDataModifier);
    }
}

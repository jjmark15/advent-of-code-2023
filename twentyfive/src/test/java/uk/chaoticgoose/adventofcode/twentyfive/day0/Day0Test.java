package uk.chaoticgoose.adventofcode.twentyfive.day0;

import org.junit.jupiter.api.Test;
import uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.chaoticgoose.adventofcode.twentyfive.utils.TestInputLoader.InputDataModifier.SHORT;

class Day0Test {
    private final TestInputLoader testInputLoader = new TestInputLoader();
    private final InputParser inputParser = new InputParser();
    private final Day0Solution underTest = new Day0Solution();

    @Test
    void part1() {
        List<String> lines = testInputLoader.load(2025, 0, SHORT);

        long result = underTest.part1(inputParser.parse(lines));

        assertThat(result).isEqualTo(11L);
    }
}

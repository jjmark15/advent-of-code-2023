package uk.chaoticgoose.adventofcode.utils.mappers;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.chaoticgoose.adventofcode.utils.mappers.Mappers.mapInner;

class MappersTest {
    @Test
    void mapsNestedList() {
        List<List<Integer>> result = Stream.of(List.of("value")).map(mapInner(String::length)).toList();

        assertThat(result).isEqualTo(List.of(List.of(5)));
    }
}
package uk.chaoticgoose.adventofcode.utils.gatherers;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.chaoticgoose.adventofcode.utils.gatherers.Gatherers.mapNonNull;

class GatherersTest {

    @Nested
    class MapNonNullTest {
        @Test
        void mapsNonNullMappedValues() {
            List<Integer> result = Stream.of(1, 2, 3).gather(mapNonNull(i -> {
                int doubled = i * 2;
                if (doubled > 4) {
                    return null;
                }
                return doubled;
            })).toList();

            assertThat(result).containsExactly(2, 4);
        }

        @Test
        void acceptsNullableValues() {
            List<@Nullable Integer> input = new ArrayList<>();
            input.add(null);
            input.add(1);
            input.add(2);

            List<Integer> result = input.stream().gather(mapNonNull(i -> {
                if (i == null) return null;
                return i * 2;
            })).toList();

            assertThat(result).containsExactly(2, 4);
        }
    }
}
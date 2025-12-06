package uk.chaoticgoose.adventofcode.twentyfive.day2;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class DaySolution {
    long part1(List<IdRange> input) {
        return input.stream().flatMap(this::invalidIds).mapToLong(Long::longValue).sum();
    }

    private Stream<Long> invalidIds(IdRange r) {
        return LongStream.rangeClosed(r.start(), r.endInclusive()).boxed().filter(this::isInvalidId);
    }

    private boolean isInvalidId(long n) {
        String nString = Long.toString(n);

        if (nString.length() % 2 != 0) return false;

        return nString.substring(0, nString.length() / 2).equals(nString.substring(nString.length() / 2));
    }
}

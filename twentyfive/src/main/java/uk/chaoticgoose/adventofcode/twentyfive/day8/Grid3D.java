package uk.chaoticgoose.adventofcode.twentyfive.day8;

import com.ginsberg.gatherers4j.dto.WithIndex;

import java.util.*;

import static com.ginsberg.gatherers4j.Gatherers4j.withIndex;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;

class Grid3D {
    private final LinkedList<HashSet<Position3D>> circuits = new LinkedList<>();
    private final List<PositionsAndDistance> inner;
    private int lastConnectedIndex = 0;

    Grid3D(List<Position3D> positions) {
        this.inner = positions.stream()
            .flatMap(position -> positions.stream()
                .filter(not(position::equals))
                .map(other -> new PositionsAndDistance(position, other, position.distanceTo(other)))
                .filter(this::isAscending))
            .sorted(comparing(PositionsAndDistance::distance))
            .toList();
    }

    public List<Set<Position3D>> circuits() {
        return unmodifiableList(circuits);
    }

    public void connectNearestUnconnectedPair() {
        var proximity = inner.get(lastConnectedIndex);

        int firstCircuitIndex = findCircuitContaining(proximity.start());
        int secondCircuitIndex = findCircuitContaining(proximity.end());

        if (firstCircuitIndex >= 0 && firstCircuitIndex == secondCircuitIndex) {
            lastConnectedIndex++;
            return;
        }

        if (firstCircuitIndex == -1 && secondCircuitIndex >= 0) {
            firstCircuitIndex = secondCircuitIndex;
        }

        if (firstCircuitIndex == -1) {
            HashSet<Position3D> circuit = new HashSet<>();
            circuits.add(circuit);
            firstCircuitIndex = circuits.size() - 1;
        }

        HashSet<Position3D> circuit = circuits.get(firstCircuitIndex);

        if (secondCircuitIndex >= 0 && firstCircuitIndex != secondCircuitIndex) {
            circuit.addAll(circuits.get(secondCircuitIndex));
            circuits.remove(secondCircuitIndex);
        }

        circuit.add(proximity.start());
        circuit.add(proximity.end());

        lastConnectedIndex++;
    }

    private int findCircuitContaining(Position3D position) {
        return circuits.stream()
            .gather(withIndex())
            .filter(c -> requireNonNull(c.value()).contains(position))
            .findFirst()
            .map(WithIndex::index)
            .orElse(-1);
    }

    private long scalarValue(Position3D position) {
        return position.x() + position.y() + position.z();
    }

    private boolean isAscending(PositionsAndDistance positionsAndDistance) {
        return scalarValue(positionsAndDistance.start()) >= scalarValue(positionsAndDistance.end());
    }
}

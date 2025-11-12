package uk.chaoticgoose.adventofcode.twentyfive.utils;

import uk.chaoticgoose.jresult.Result;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class TestInputLoader {

    public List<String> load(int year, int day, InputDataModifier modifier) {
        var fileName = "/%s/day_%s_data_%s.txt".formatted(year, day, modifier.value());
        Path filePath = Path.of(Objects.requireNonNull(getClass().getResource(fileName)).getPath());
        return Result.catching(() -> Files.readAllLines(filePath)).mapFailure(RuntimeException::new).valueOrThrow();
    }

    public enum InputDataModifier {
        SHORT,
        LONG;

        String value() {
            return switch (this) {
                case SHORT -> "short";
                case LONG -> "long";
            };
        }
    }
}

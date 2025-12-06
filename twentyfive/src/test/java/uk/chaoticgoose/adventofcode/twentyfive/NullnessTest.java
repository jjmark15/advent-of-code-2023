package uk.chaoticgoose.adventofcode.twentyfive;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import uk.chaoticgoose.adventofcode.utils.AbstractNullnessTest;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;

public class NullnessTest extends AbstractNullnessTest {
    private static final JavaClasses CLASSES = new ClassFileImporter()
        .withImportOption(DO_NOT_INCLUDE_TESTS)
        .importPackages("uk.chaoticgoose.adventofcode.twentyfive");

    @Override
    public JavaClasses targetClasses() {
        return CLASSES;
    }
}
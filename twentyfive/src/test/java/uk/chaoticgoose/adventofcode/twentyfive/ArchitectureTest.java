package uk.chaoticgoose.adventofcode.twentyfive;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {
    private static final JavaClasses CLASSES = new ClassFileImporter()
        .withImportOption(DO_NOT_INCLUDE_TESTS)
        .importPackages("uk.chaoticgoose.adventofcode.twentyfive");

    @Test
    void dayClassesArePackagePrivate() {
        classes().that().resideInAPackage("uk.chaoticgoose.adventofcode.twentyfive..")
            .should().notBePublic()
            .check(CLASSES);
    }
}

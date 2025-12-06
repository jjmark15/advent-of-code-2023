package uk.chaoticgoose.adventofcode.utils;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TEST_FIXTURES;

public class NullnessTest extends AbstractNullnessTest {
    private static final JavaClasses CLASSES = new ClassFileImporter()
        .withImportOption(DO_NOT_INCLUDE_TESTS)
        .withImportOption(DO_NOT_INCLUDE_TEST_FIXTURES)
        .importPackages("uk.chaoticgoose.adventofcode.utils");

    @Override
    public JavaClasses targetClasses() {
        return CLASSES;
    }
}
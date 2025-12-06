package uk.chaoticgoose.adventofcode.utils;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.conditions.ArchConditions.not;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@NullMarked
public abstract class AbstractNullnessTest {

    public abstract JavaClasses targetClasses();

    ArchCondition<JavaMethod> beAnnotatedWithNullable =
            new ArchCondition<>("be annotated with @Nullable") {
                @Override
                public void check(JavaMethod method, ConditionEvents events) {
                    var isAnnotated = method.reflect().getAnnotatedReturnType().getAnnotation(Nullable.class) != null;

                    if (isAnnotated) {
                        String message = "Method %s is annotated with @Nullable".formatted(method.getFullName());
                        events.add(SimpleConditionEvent.satisfied(method, message));
                    }
                }
            };

    @Test
    void nullableReturnMethodsMustFollowNamingConvention() {
        methods()
            .that().areNotPrivate()
            .and().areDeclaredInClassesThat().areNotRecords()
            .and().haveNameNotEndingWith("OrNull")
            .should(not(beAnnotatedWithNullable))
            .allowEmptyShould(true).check(targetClasses());
    }
}
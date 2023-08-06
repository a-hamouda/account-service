package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Architecture Dependencies")
class DependencyRuleTest {

    private static final String ROOT_PACKAGE = "com.example.accountservice";
    private static final String CORE_PACKAGE = "%s.core..".formatted(ROOT_PACKAGE);
    private static final String JAVA_STANDARD_PACKAGE = "java..";
    private static final String JAVA_EXTENSION_PACKAGE = "javax..";
    private final JavaClasses allClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS).importPackages(ROOT_PACKAGE);

    @Test
    @DisplayName("Core classes should only depend on standard or other core classes")
    void core() {
        final var rule = ArchRuleDefinition.classes().that().resideInAPackage(CORE_PACKAGE).should()
                                           .onlyDependOnClassesThat().resideInAnyPackage(CORE_PACKAGE,
                        JAVA_STANDARD_PACKAGE, JAVA_EXTENSION_PACKAGE);
        rule.check(allClasses);
    }
}

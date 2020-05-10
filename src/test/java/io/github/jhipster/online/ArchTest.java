package io.github.jhipster.online;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("io.github.jhipster.online");

        noClasses()
            .that()
                .resideInAnyPackage("io.github.jhipster.online.service..")
            .or()
                .resideInAnyPackage("io.github.jhipster.online.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..io.github.jhipster.online.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}

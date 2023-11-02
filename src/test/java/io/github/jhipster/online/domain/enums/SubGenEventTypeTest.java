package io.github.jhipster.online.domain.enums;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SubGenEventTypeTest {

    @Test
    public void testGetDatabaseValue() {
        assertEquals("heroku", SubGenEventType.HEROKU.getDatabaseValue());
        assertEquals("cloudfoundry", SubGenEventType.CLOUDFOUNDRY.getDatabaseValue());
        assertEquals("aws", SubGenEventType.AWS.getDatabaseValue());
        assertEquals("ci-cd", SubGenEventType.CI_CD.getDatabaseValue());
        assertEquals("", SubGenEventType.CLIENT.getDatabaseValue());
        assertEquals("docker-compose", SubGenEventType.DOCKER_COMPOSE.getDatabaseValue());
        assertEquals("export-jdl", SubGenEventType.EXPORT_JDL.getDatabaseValue());
        assertEquals("import-jdl", SubGenEventType.IMPORT_JDL.getDatabaseValue());
        assertEquals("kubernetes", SubGenEventType.KUBERNETES.getDatabaseValue());
        assertEquals("languages", SubGenEventType.LANGUAGES.getDatabaseValue());
        assertEquals("openshift", SubGenEventType.OPENSHIFT.getDatabaseValue());
        assertEquals("rancher-compose", SubGenEventType.RANCHER_COMPOSE.getDatabaseValue());
        assertEquals("server", SubGenEventType.SERVER.getDatabaseValue());
        assertEquals("spring-controller", SubGenEventType.SPRING_CONTROLLER.getDatabaseValue());
        assertEquals("upgrade", SubGenEventType.UPGRADE.getDatabaseValue());
    }

    @Test
    public void testGetDeploymentTools() {
        SubGenEventType[] deploymentTools = SubGenEventType.getDeploymentTools();
        assertEquals(5, deploymentTools.length);
        assertArrayEquals(
            new SubGenEventType[] {
                SubGenEventType.HEROKU,
                SubGenEventType.CLOUDFOUNDRY,
                SubGenEventType.AWS,
                SubGenEventType.OPENSHIFT,
                SubGenEventType.KUBERNETES
            },
            deploymentTools
        );
    }
}

package io.github.jhipster.online.service.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class CiCdToolTest {

    @Test
    public void testGetByName() {
        Optional<CiCdTool> ciCdTool = CiCdTool.getByName("TRAVIS");
        assertEquals(CiCdTool.TRAVIS, ciCdTool.orElse(null));

        ciCdTool = CiCdTool.getByName("JENKINS");
        assertEquals(CiCdTool.JENKINS, ciCdTool.orElse(null));

        /* testing a CI_CD tool which dont exist */
        ciCdTool = CiCdTool.getByName("UNKNOWN_TOOL");
        assertEquals(Optional.empty(), ciCdTool);
    }

    @Test
    public void testCommand() {
        assertEquals("travis", CiCdTool.TRAVIS.command());
        assertEquals("github", CiCdTool.GITHUB.command());
        assertEquals("circle", CiCdTool.CIRCLE.command());
    }

    @Test
    public void testBranchName() {
        assertEquals("jhipster-travis-complement", CiCdTool.TRAVIS.branchName("complement"));
        assertEquals("jhipster-github-complement", CiCdTool.GITHUB.branchName("complement"));
    }

    @Test
    public void testGetCiCdToolName() {
        assertEquals("GitLab", CiCdTool.GITLAB.getCiCdToolName());
        assertEquals("GitHub", CiCdTool.GITHUB.getCiCdToolName());
        assertEquals("Azure", CiCdTool.AZURE.getCiCdToolName());
        assertEquals("Circle", CiCdTool.CIRCLE.getCiCdToolName());
    }
}

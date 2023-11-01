package io.github.jhipster.online.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityStatColumnTest {
    @Test
    public void testGetDatabaseValue() {
        assertEquals("fields", EntityStatColumn.FIELDS.getDatabaseValue());
        assertEquals("relationships", EntityStatColumn.RELATIONSHIPS.getDatabaseValue());
        assertEquals("pagination", EntityStatColumn.PAGINATION.getDatabaseValue());
        assertEquals("dto", EntityStatColumn.DTO.getDatabaseValue());
        assertEquals("service", EntityStatColumn.SERVICE.getDatabaseValue());
        assertEquals("fluentMethods", EntityStatColumn.FLUENT_METHODS.getDatabaseValue());
        assertEquals("date", EntityStatColumn.DATE.getDatabaseValue());
    }
}

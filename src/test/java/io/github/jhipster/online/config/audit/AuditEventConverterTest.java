/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jhipster.online.config.audit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.github.jhipster.online.domain.PersistentAuditEvent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

class AuditEventConverterTest {

    private AuditEventConverter converter = new AuditEventConverter();

    @Test
    void convertToAuditEvent() {
        Instant oneHourAgo = Instant.now().minusSeconds(3600);
        Instant tenMinutesAgo = Instant.now().minusSeconds(600);
        Iterable<PersistentAuditEvent> persistentAuditEvents = Arrays.asList(
            persistedAuditEvent(oneHourAgo),
            persistedAuditEvent(tenMinutesAgo)
        );

        final List<AuditEvent> auditEvents = converter.convertToAuditEvent(persistentAuditEvents);

        assertThat(auditEvents).hasSize(2).extracting("principal", "type").containsOnly(tuple("test-user", "test-type"));
        assertThat(auditEvents.get(0).getTimestamp()).isEqualTo(oneHourAgo);
        assertThat(auditEvents.get(0).getData()).contains(entry("test-key", "test-value"), entry("test-key", "test-value"));
        assertThat(auditEvents.get(1).getTimestamp()).isEqualTo(tenMinutesAgo);
        assertThat(auditEvents.get(1).getData()).contains(entry("test-key", "test-value"), entry("test-key", "test-value"));
    }

    @Test
    void convertToAuditEventNullParam() {
        final List<AuditEvent> auditEvents = converter.convertToAuditEvent((Iterable<PersistentAuditEvent>) null);
        assertThat(auditEvents).isNotNull().isEmpty();
    }

    @Test
    void convertToAuditEventNullEvent() {
        final List<PersistentAuditEvent> persistentAuditEvents = new ArrayList<>();
        persistentAuditEvents.add(null);
        final List<AuditEvent> auditEvents = converter.convertToAuditEvent(persistentAuditEvents);

        assertThat(auditEvents).isNotNull().isNotEmpty();
    }

    @Test
    void convertDataToStrings() {
        Map<String, Object> data = new HashMap<>();
        data.put("test-key", "test-value");
        String remoteAddress = "https://github.com/jhipster/jhipster-online";
        String sessionId = "fb2e77d.47a0479900504cb3ab4a1f626d174d2d";
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        given(mockRequest.getRemoteAddr()).willReturn(remoteAddress);
        HttpSession mockSession = mock(HttpSession.class);
        given(mockSession.getId()).willReturn(sessionId);
        given(mockRequest.getSession(false)).willReturn(mockSession);

        data.put("auth-details", new WebAuthenticationDetails(mockRequest));

        final Map<String, String> stringMap = converter.convertDataToStrings(data);

        assertThat(stringMap)
            .containsOnly(entry("test-key", "test-value"), entry("remoteAddress", remoteAddress), entry("sessionId", sessionId));
    }

    PersistentAuditEvent persistedAuditEvent(Instant auditEventDate) {
        final PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
        persistentAuditEvent.setAuditEventDate(auditEventDate);
        persistentAuditEvent.setPrincipal("test-user");
        persistentAuditEvent.setAuditEventType("test-type");
        Map<String, String> data = new HashMap<>();
        data.put("test-key", "test-value");
        persistentAuditEvent.setData(data);
        return persistentAuditEvent;
    }
}

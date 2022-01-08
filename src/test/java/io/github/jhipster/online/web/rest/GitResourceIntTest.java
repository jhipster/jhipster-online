/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
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
package io.github.jhipster.online.web.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.service.GithubService;
import io.github.jhipster.online.service.GitlabService;
import io.github.jhipster.online.service.UserService;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the GitResource REST controller.
 *
 * @see GitResource
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GitResourceIntTest {

    @Autowired
    private HttpMessageConverter<?>[] httpMessageConverters;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ApplicationProperties mockApplicationProperties;

    @Mock
    private UserService mockUserService;

    @Mock
    private GithubService mockGithubService;

    @Mock
    private GitlabService mockGitlabService;

    private MockMvc restMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        GitResource gitResource = new GitResource(mockApplicationProperties, mockUserService, mockGithubService, mockGitlabService);

        this.restMvc =
            MockMvcBuilders
                .standaloneSetup(gitResource)
                .setMessageConverters(httpMessageConverters)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    void testGetGitConfiguration() throws Exception {
        when(mockGithubService.getClientId()).thenReturn("fzerfzer54fer8gf48");
        when(mockGithubService.getHost()).thenReturn("http://github.com");
        when(mockGithubService.isEnabled()).thenReturn(true);

        when(mockGitlabService.getClientId()).thenReturn("rf478erf48erg4er8g41ref47ef481098g49");
        when(mockGitlabService.getHost()).thenReturn("http//gitlab.com");
        when(mockGitlabService.getRedirectUri()).thenReturn("http//localhost:9000/api/callback/github");
        when(mockGitlabService.isEnabled()).thenReturn(true);

        restMvc
            .perform(get("/api/git/config", "TestProvider").accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testSaveTokenWithUnknownGitProvider() throws Exception {
        final String code = "ret66spihj6sio4bud2";
        restMvc
            .perform(post("/api/{gitProvider}/save-token", "TestProvider").contentType(MediaType.APPLICATION_JSON).content(code))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testRefreshGithubWithUnknownProvider() throws Exception {
        final String unavailableGitProvider = "TestProvider";
        restMvc
            .perform(post("/api/{gitProvider}/refresh", "TestProvider").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Unknown git provider: " + unavailableGitProvider));
    }
}

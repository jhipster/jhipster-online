/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.service.GithubService;
import io.github.jhipster.online.service.GitlabService;
import io.github.jhipster.online.service.UserService;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GitResource REST controller.
 *
 * @see GitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitResourceIntTest {

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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        GitResource gitResource = new GitResource(mockApplicationProperties, mockUserService, mockGithubService, mockGitlabService);

        this.restMvc = MockMvcBuilders.standaloneSetup(gitResource)
            .setMessageConverters(httpMessageConverters)
            .setControllerAdvice(exceptionTranslator)
            .build();
    }

    @Test
    public void testGetClientIdWithUnknownGitProvider() throws Exception {
        restMvc.perform(
            get("/api/{gitProvider}/client-id", "Microsoft-GitHub")
                .accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_VALUE))
            .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetClientIdWithExistingGitProvider() throws Exception {
        when(mockApplicationProperties.getGitlab().getClientId()).thenReturn("T--Xx_|o|_xX--T");

        restMvc.perform(
            get("/api/{gitProvider}/client-id", "GitLab")
                .accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_VALUE))
            .andExpect(content().contentType(TestUtil.TEXT_PLAIN))
            .andExpect(status().isOk());
    }

    @Test
    public void testSaveTokenWithUnknownGitProvider() throws Exception {
        final String code = "ret66spihj6sio4bud2";
        restMvc.perform(
            post("/api/{gitProvider}/save-token", "Microsoft-GitHub")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(code))
            .andExpect(status().isInternalServerError());
    }

    @Test
    public void testRefreshGithubWithUnknownProvider() throws Exception {
        final String unavailableGitProvider = "Microsoft-GitHub";
        restMvc.perform(
            post("/api/{gitProvider}/refresh", "Microsoft-GitHub")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Unknown git provider: " + unavailableGitProvider));
    }
}

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

package io.github.jhipster.online.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogsService {

    private final Logger log = LoggerFactory.getLogger(LogsService.class);

    private Cache<String, String> logsCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    public synchronized void addLog(String logID, String logMessage) {
        log.info("log `{}` : {}", logID, logMessage);
        String logMessages = logsCache.getIfPresent(logID);
        if (logMessages == null) {
            logMessages = logMessage + "\n";
        } else {
            logMessages += logMessage + "\n";
        }
        logsCache.put(logID, logMessages);
    }

    public synchronized String getLogs(String logId) {
        String logs = logsCache.getIfPresent(logId);
        logsCache.invalidate(logId);
        return logs;
    }
}

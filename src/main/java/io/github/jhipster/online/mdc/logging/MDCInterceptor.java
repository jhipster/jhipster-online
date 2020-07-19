package io.github.jhipster.online.mdc.logging;

import io.github.jhipster.online.security.SecurityUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MDCInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (SecurityUtils.getCurrentUserLogin().isPresent())
            MDC.put("user.login", SecurityUtils.getCurrentUserLogin().get());
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (SecurityUtils.getCurrentUserLogin().isPresent())
            MDC.remove("user.login");
    }
}

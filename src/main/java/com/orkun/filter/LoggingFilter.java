package com.orkun.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if ( this.shouldLogRequest(httpServletRequest) ) {
            log.info("Request " + httpServletRequest.getRequestURL().toString() + ", method: " + httpServletRequest.getMethod());
        }

        chain.doFilter(request, response);

        if ( this.shouldLogRequest(httpServletRequest) ) {
            log.info("Response status: " + httpServletResponse.getStatus());
        }
    }

    private Boolean shouldLogRequest(HttpServletRequest request) {

        return !request.getServletPath().matches("(?i).*actuator.*")
                && !request.getServletPath().matches("(?i).*swagger.*")
                && !request.getServletPath().matches("(?i).*api-docs.*");
    }
}

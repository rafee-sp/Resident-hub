package com.rafee.residenthub.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;

@Component
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "X-REQUEST-ID";
    private static final String REQUEST_ID_MDC_KEY = "requestId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestId = request.getHeader(REQUEST_ID_HEADER);

        if (!StringUtils.hasText(requestId)) {
            requestId = "";
        }

        MDC.put(REQUEST_ID_MDC_KEY, requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        try {

            filterChain.doFilter(servletRequest, servletResponse);

        } finally {
            MDC.remove(REQUEST_ID_MDC_KEY);
        }
    }
}

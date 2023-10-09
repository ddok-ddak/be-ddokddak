package com.ddokddak.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        // TODO
        this.setMdc((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("Request served");
        MDC.clear();
    }

    public void setMdc(HttpServletRequest request){
        String requestId = request.getHeader("X-Request-ID");
        MDC.put("request_id",  StringUtils.defaultString(requestId, UUID.randomUUID().toString().replaceAll("-", "")));
        //MDC.put("request_ip", request.getRemoteAddr());
        MDC.put("request_ip", StringUtils.defaultString(request.getHeader("X-REAL-IP"), ""));

        MDC.put("request_context_path", request.getContextPath());
        MDC.put("request_url", request.getRequestURI());
        MDC.put("request_method", request.getMethod());
        //MDC.put("request_time", new Date().toString());
        //MDC.put("request_header", request.getHeader(TokenProvider.HEADER_NAME));
        MDC.put("request_query_string", request.getQueryString());
    }
}
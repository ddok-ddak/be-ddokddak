package com.ddokddak.auth.filter;

import com.ddokddak.common.dto.CommonErrorResponse;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.BaseException;
import com.ddokddak.common.exception.type.ExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);
        } catch (CustomApiException ex) {
            this.setErrorResponse(response, ex.getExceptionType());
        } catch (Exception ex) {
            this.setErrorResponse(response, BaseException.SERVER_ERROR);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ExceptionType exceptionType) {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(exceptionType.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        CommonErrorResponse errorResponse = new CommonErrorResponse(exceptionType.getMessage(), exceptionType);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.ddokddak.common.api;

import com.ddokddak.common.dto.CommonErrorResponse;
import com.ddokddak.common.exception.type.BaseException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public ResponseEntity<Object> handleNoHandlerFoundException(HttpServletResponse response, HttpServletRequest request) {

        if (Objects.equals(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            CommonErrorResponse commonErrorResponse = new CommonErrorResponse("Bad Request", BaseException.SERVER_ERROR);
            return new ResponseEntity<>(commonErrorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    }
}

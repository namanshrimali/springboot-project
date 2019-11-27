package com.westpac.democode_review.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Date;
import java.util.HashMap;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<HashMap<String, String>> handleProjectNotFound(ProjectNotFoundException projectNotFoundException) {
        logger.error("Exception occurred :: requested project does not exists in the database , timestamp :: {}" ,new Date());
        HashMap  <String, String> result = new HashMap<>();
        result.put("message", projectNotFoundException.getMessage());
        result.put("error","true");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}

package com.myfitbody.controllers;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class HttpResponse<T> {
    private Instant timestamp;
    private HttpStatus status;
    private int statusCode;
    private String message;
    private Map<String, T> data;
}

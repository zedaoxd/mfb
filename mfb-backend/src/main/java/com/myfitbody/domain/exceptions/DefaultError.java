package com.myfitbody.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultError {
    private Instant timestamp;
    private HttpStatus status;
    private int statusCode;
    private String message;
    private String path;
}

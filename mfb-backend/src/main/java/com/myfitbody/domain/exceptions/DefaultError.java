package com.myfitbody.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}

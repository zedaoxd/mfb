package com.myfitbody.domain.exceptions;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends DefaultError {

    private List<FieldMessage> errors = new ArrayList<>();

}

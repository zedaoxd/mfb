package com.myfitbody.validations;

import com.myfitbody.domain.exceptions.FieldMessage;
import com.myfitbody.domain.user.UserCreateDTO;
import com.myfitbody.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserCreateValidator implements ConstraintValidator<UserCreateValid, UserCreateDTO> {

    private final UserRepository repository;

    @Override
    public boolean isValid(UserCreateDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (repository.existsUserByEmailIgnoreCase(dto.email())) {
            list.add(new FieldMessage("email", "this email already exists"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}


package com.myfitbody.validations;

import com.myfitbody.domain.exceptions.FieldMessage;
import com.myfitbody.domain.user.UserResetPasswordDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
public class UserResetPasswordValidator implements ConstraintValidator<UserResetPasswordValid, UserResetPasswordDTO> {

    @Override
    public boolean isValid(UserResetPasswordDTO dto, ConstraintValidatorContext constraintValidatorContext) {

        List<FieldMessage> list = new ArrayList<>();

        if (!dto.password().equals(dto.confirmPassword())) {
            list.add(new FieldMessage("confirmPassword", "Passwords do not match"));
        }

        for (FieldMessage e : list) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}

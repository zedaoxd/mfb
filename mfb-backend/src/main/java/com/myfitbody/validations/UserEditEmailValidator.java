package com.myfitbody.validations;


import com.myfitbody.domain.exceptions.FieldMessage;
import com.myfitbody.domain.exceptions.ResourceNotFoundException;
import com.myfitbody.domain.user.User;
import com.myfitbody.domain.user.UserEditEmailDTO;
import com.myfitbody.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class UserEditEmailValidator implements ConstraintValidator<UserEditEmailValid, UserEditEmailDTO> {

    private final HttpServletRequest request;
    private final UserRepository repository;

    @Override
    public boolean isValid(UserEditEmailDTO dto, ConstraintValidatorContext context) {

        var uriVars = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        UUID id = UUID.fromString((String) ((Map<?, ?>) uriVars).get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User entity = repository
                .findByEmailIgnoreCase(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found, email: " + dto.email()));

        if (entity != null && !entity.getId().equals(id)) {
            list.add(new FieldMessage("email", "This email is already in use by another user"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}

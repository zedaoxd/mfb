package com.myfitbody.services.impl;

import com.myfitbody.domain.email.EmailBodyType;
import com.myfitbody.repositories.EmailBodyTypeRepository;
import com.myfitbody.services.contracts.EmailBodyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailBodyTypeImpl implements EmailBodyTypeService {

    private final EmailBodyTypeRepository emailBodyTypeRepository;

    @Override
    public String formatEmailBody(EmailBodyType type, Map<String, String> params) {
        String body = emailBodyTypeRepository.findByEmailBodyType(type).getBody();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            body = body.replace(entry.getKey(), entry.getValue());
        }
        return body;
    }
}

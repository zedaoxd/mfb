package com.myfitbody.services.contracts;

import com.myfitbody.domain.email.EmailBodyType;

import java.util.Map;

public interface EmailBodyTypeService {

    String formatEmailBody(EmailBodyType type, Map<String, String> params);
}

package com.myfitbody.services.contracts;

public interface EmailService {

    void sendEmail(String to, String subject, String body, String... attachmentsPath);
}

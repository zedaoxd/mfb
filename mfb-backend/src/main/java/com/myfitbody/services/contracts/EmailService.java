package com.myfitbody.services.contracts;

public interface EmailService {

    void sendHtmlEmail(String to, String subject, String body);
    void sendHtmlEmailWithAttachments(String to, String subject, String body, String... attachmentsPath);
}

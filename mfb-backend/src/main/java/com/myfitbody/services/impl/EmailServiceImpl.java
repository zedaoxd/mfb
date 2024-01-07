package com.myfitbody.services.impl;

import com.myfitbody.services.contracts.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String UTF_8_ENCODING = "UTF-8";
    private static final String EMAIL_TEMPLATE = "email-template";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendEmail(String to, String subject, String body, String... attachmentsPath) {
        try {
            Context context = new Context();
            context.setVariable("body", body);
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setFrom(fromEmail, "MyFitBody");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            for (String attachmentPath : attachmentsPath) {
                helper.addAttachment(attachmentPath, new ClassPathResource(attachmentPath));
            }
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}

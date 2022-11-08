package com.example.hesanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    public static volatile Builder BUILDER;

    public MailService(JavaMailSender mailSender) {
        if (BUILDER == null) {
            synchronized (this) {
                if (BUILDER == null) {
                    BUILDER = new Builder();
                }
            }
        }
    }

    public class Builder {
        private String mailFrom;
        private String mailTo;
        private String subject;
        private String message;

        public Builder mailFrom(String mailFrom) {
            this.mailFrom = mailFrom;
            return this;
        }

        public Builder mailTo(String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public void send() {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mailFrom);
            mailMessage.setTo(mailTo);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }
    }
}

package com.example.hesanger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender geMailSender() throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getResourceAsStream("/application.properties"));
        String host = props.getProperty("spring.mail.host");
        String userName = props.getProperty("spring.mail.username");
        String password = props.getProperty("spring.mail.password");
        int port = Integer.parseInt(props.getProperty("spring.mail.port"));
        String protocol = props.getProperty("spring.mail.protocol");
        String debug = props.getProperty("mail.debug");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);

        Properties javaMailSenderProps = javaMailSender.getJavaMailProperties();
        javaMailSenderProps.setProperty("mail.transport.protocol", protocol);
        javaMailSenderProps.setProperty("mail.debug", debug);

        return javaMailSender;
    }
}

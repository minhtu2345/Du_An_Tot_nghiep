package com.example.demo.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = getFile("src/main/resources/application.properties");
            properties.load(fileInputStream);

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            String host=properties.getProperty("spring.mail.host");
            Integer port=Integer.valueOf(properties.getProperty("spring.mail.port"));
            String username=properties.getProperty("spring.mail.username");
            String password=properties.getProperty("spring.mail.password");

            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            return mailSender;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }

    public FileInputStream getFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return new FileInputStream(file);
            } else {
                System.err.println("File not found: " + path);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return null;
    }
}

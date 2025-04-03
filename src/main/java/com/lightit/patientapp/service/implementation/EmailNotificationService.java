package com.lightit.patientapp.service.implementation;

import com.lightit.patientapp.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService implements INotificationService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void notify(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Patient Registration Confirmation");
        message.setText("Hi " + name + ",\n\nYour patient registration was successful!\n\nRegards,\nThe Team");

        mailSender.send(message);
    }
}

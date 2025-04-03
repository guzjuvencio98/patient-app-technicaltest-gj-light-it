package com.lightit.patientapp.controller;

import com.lightit.patientapp.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestMailController {

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/api/test-email")
    public String testEmail() {
        notificationService.notify("mail@email.com", "Test");
        return "Email sent!";
    }
}
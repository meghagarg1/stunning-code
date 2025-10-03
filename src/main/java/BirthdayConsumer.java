package com.example.stunningcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class BirthdayConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "birthday-topic", groupId = "birthday-group")
    public void listen(Employee emp) {
        // Temporarily disabled
        System.out.println("BirthdayConsumer disabled - no Kafka events to process.");
    }

    private void sendEmail(String to, String subject, String body) {
        // Disabled
    }
}

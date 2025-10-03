package com.example.stunningcode;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BirthdayProducer {

    @Scheduled(cron = "0 0 9 * * ?")
    public void publishBirthdayEvents() {
        // Temporarily disabled for Spring Boot focus
        System.out.println("BirthdayProducer disabled - focusing on Spring Boot email scheduling.");
    }
}

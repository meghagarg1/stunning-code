package com.example.stunningcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
    public void sendBirthdayGreetingsScheduled() {
        List<Employee> employees = readEmployeesFromCSV("employees.csv");
        sendBirthdayGreetings(employees);
    }


    public List<Employee> readEmployeesFromCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                String[] values = line.split(",");
                if (values.length == 5) {
                    employees.add(new Employee(
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            values[3].trim(),
                            values[4].trim()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void sendBirthdayGreetings(List<Employee> employees) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Employee emp : employees) {
            LocalDate dob = LocalDate.parse(emp.getDob(), formatter);
            if (dob.getMonth() == today.getMonth() && dob.getDayOfMonth() == today.getDayOfMonth()) {
                System.out.println("Sending email to " + emp.getEmail());
                sendEmail(emp.getEmail(), "Happy Birthday " + emp.getFirstName(), "Wishing you a wonderful birthday!");
            }
        }
        System.out.println("Birthday greetings sent where applicable.");
    }

    private void sendEmail(String to, String subject, String body) {
        if (mailSender == null) {
            System.err.println("JavaMailSender is not configured. Check application.properties and dependencies.");
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("Email sent to " + to);
    }
}
